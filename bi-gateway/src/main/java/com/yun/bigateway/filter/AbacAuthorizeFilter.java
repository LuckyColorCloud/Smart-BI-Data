//package com.yun.bigateway.filter;
//
//import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
//import com.sobercoding.loopauth.context.LoopAuthContextThreadLocal;
//import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthContextHolder;
//import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthRequestForWebFlux;
//import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthResponseForWebFlux;
//import com.sobercoding.loopauth.springbootwebfluxstarter.context.LoopAuthStorageForWebFlux;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @author Sober
// */
////@Component
//public class AbacAuthorizeFilter implements GlobalFilter, Ordered {
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        try {
//            //写入上下文
//            LoopAuthContextThreadLocal.setContext(new LoopAuthRequestForWebFlux(exchange.getRequest()),
//                    new LoopAuthResponseForWebFlux(exchange.getResponse()),
//                    new LoopAuthStorageForWebFlux(exchange));
//
//            // 路由
//            String route = exchange.getRequest().getURI().getPath();
//            // ABAC 鉴权校验
//            LoopAuthAbac.check(route, exchange.getRequest().getMethodValue());
//        } catch (Throwable e) {
//            e.printStackTrace();
//            // 异常处理
//            String result = e.getMessage();
//            // 写入输出流
//            if(exchange.getResponse().getHeaders().getFirst("Content-Type") == null) {
//                exchange.getResponse().getHeaders().set("Content-Type", "text/plain; charset=utf-8");
//            }
//            // 清除上下文
//            LoopAuthContextThreadLocal.clearContextBox();
//            return exchange.getResponse()
//                    .writeWith(
//                            Mono.just(
//                                    exchange.getResponse()
//                                            .bufferFactory()
//                                            .wrap(result.getBytes())
//                            )
//                    );
//        }
//        // 执行
//        return chain.filter(exchange)
//                .subscriberContext(ctx -> {
//                    ctx = ctx.put(LoopAuthContextHolder.CONTEXT_KEY, exchange);
//                    return ctx;
//                })
//                .doFinally(r -> {
//                    // 清除上下文
//                    LoopAuthContextThreadLocal.clearContextBox();
//                });
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
