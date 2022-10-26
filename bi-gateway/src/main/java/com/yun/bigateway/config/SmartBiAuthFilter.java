package com.yun.bigateway.config;

import cn.hutool.json.JSONUtil;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.context.LoopAuthContextThreadLocal;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.api.SecurityContextFeign;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Sober
 */
@Component
public class SmartBiAuthFilter implements GlobalFilter, Ordered {

    @Resource
    private SecurityContextFeign securityContextFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try{
            // 请求security服务鉴权
            CompletableFuture<Result<Boolean>> result = CompletableFuture.supplyAsync(
                    () -> securityContextFeign.checkAbAc(
                            exchange.getRequest().getURI().getPath(),
                            exchange.getRequest().getMethodValue()
                    )
            );
            if (!result.get().getResult()){
                // 写入输出流
                if(exchange.getResponse().getHeaders().getFirst("Content-Type") == null) {
                    exchange.getResponse().getHeaders().set("Content-Type", "application/json; charset=utf-8");
                }
                return exchange.getResponse()
                        .writeWith(
                                Mono.just(
                                        exchange.getResponse()
                                                .bufferFactory()
                                                .wrap(JSONUtil.toJsonStr(result.get()).getBytes())
                                )
                        );
            }
            // 写入请求头

        } catch (Throwable e) {
            e.printStackTrace();
            Result<String> result = Result.ERROR(Result.ResultEnum.ERROR);
            // 写入输出流
            if(exchange.getResponse().getHeaders().getFirst("Content-Type") == null) {
                exchange.getResponse().getHeaders().set("Content-Type", "application/json; charset=utf-8");
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

        // 放行
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
