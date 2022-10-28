package com.yun.bigateway.config;

import cn.hutool.json.JSONUtil;
import com.yun.bidatacommon.constant.SecurityConfig;
import com.yun.bidatacommon.security.UserSessionInfo;
import com.yun.bidatacommon.vo.Result;
import com.yun.bigateway.context.ContextThreadLocal;
import com.yun.bigateway.context.RequestForWebFlux;
import com.yun.bigateway.fegin.SecurityContextFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 请求拦截器
 * @author Sober
 */
@Slf4j
@Component
public class SmartBiAuthFilter implements GlobalFilter, Ordered {

    @Resource
    private SecurityContextFeign securityContextFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try{
            // 异步请求security服务鉴权
            CompletableFuture<Result<UserSessionInfo>> result = CompletableFuture.supplyAsync(
                    () -> {
                        //写入上下文
                        ContextThreadLocal.setRequest(new RequestForWebFlux(exchange.getRequest()));
                        return securityContextFeign.checkAbAc(
                                exchange.getRequest().getURI().getPath(),
                                exchange.getRequest().getMethodValue()
                        );
                    }
            ).whenComplete((v,e) -> {
                // 清除上下文
                ContextThreadLocal.clearRequest();
            });
            Result<UserSessionInfo> resultInfo = result.join();
            if (resultInfo.getCode().equals(Result.ResultEnum.ILLEGAL_REQUEST.getRespCode())){
                return error(exchange, Result.ERROR(Result.ResultEnum.ILLEGAL_REQUEST));
            }else {
                // UserSessionInfo为空则代表非拦截路由，无需写入请求头
                if (Optional.ofNullable(resultInfo.getResult()).isPresent()) {
                    // 写入请求头
                    ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders -> {
                        httpHeaders.add(SecurityConfig.getSessionName(), resultInfo.getResult().toJson());
                    }).build();
                    ServerWebExchange finalExchange = exchange.mutate().request(host).build();
                    // 放行
                    return chain.filter(finalExchange);
                }
            }
        } catch (Throwable e) {
            log.error("未知异常:" + e.getMessage());
            Result<Object> result = Result.ERROR(Result.ResultEnum.ERROR);
            return error(exchange, result);
        }

        // 放行
        return chain.filter(exchange);
    }

    /**
     * 拦截
     * @author Sober
     * @param exchange 上下文
     * @param result 返回信息
     * @return reactor.core.publisher.Mono<java.lang.Void>
     */
    public Mono<Void> error(ServerWebExchange exchange, Result<Object> result) {
        String first = "Content-Type";
        String dataType = "application/json; charset=utf-8";
        // 写入输出流
        if(exchange.getResponse().getHeaders().getFirst(first) == null) {
            exchange.getResponse().getHeaders().set(first, dataType);
        }
        return exchange.getResponse()
                .writeWith(
                        Mono.just(
                                exchange.getResponse()
                                        .bufferFactory()
                                        .wrap(JSONUtil.toJsonStr(result).getBytes())
                        )
                );
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
