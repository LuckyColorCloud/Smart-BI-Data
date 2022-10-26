package com.yun.bigateway.config;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.model.builder.AbacPolicyFunBuilder;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.springbootwebfluxstarter.filter.LoopAuthWebFluxFilter;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bisecurity.api.SecurityContextFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sober
 */
@Configuration
public class LoopAuthConfig {

    @Autowired
    private SecurityContextFeign securityContextFeign;

    @Bean
    public void setAbacConfig() {
        AbacStrategy.abacPoAndSuMap = new AbacPolicyFunBuilder()
                // 是否登录规则
                .setPolicyFun("isLogin",
                        // 创建规则校验及获取当前值的方式
                        new AbacPoAndSu()
                                // 创建校验方式  value为当前值即setSupplierMap提供的值
                                // rule为规则的值即 Policy setProperty 的值
                                .setMaFunction((value, rule) -> {
                                    securityContextFeign.isLogin(1L);
                                })
                                // 获得value方式
                                .setSupplierMap(() -> true)
                )
                // loginId 校验规则
                .loginId()
                .setLoginId(() -> {
                    String loginId = securityContextFeign.getLoginId(1L).getResult();
                    return loginId;
                })
                .build();
    }

    /**
     * 注册 [LoopAuth 全局过滤器]
     */
    @Bean
    public LoopAuthWebFluxFilter getLoopAuthServletFilter() {
        return new LoopAuthWebFluxFilter()
                .addInclude("/**")
                .addExclude("/test/login", LoopAuthHttpMode.GET)
                // 认证函数: 每次请求执行
                .setLoopAuthFilter((isIntercept, route, exchange) -> {
                    if (isIntercept) {
                        // ABAC 鉴权校验
                        LoopAuthAbac.check(route, exchange.getRequest().getMethodValue());
                    }
                })
                .setLoopAuthErrorFilter(e -> Result.ERROR(e.getMessage()));
    }

}
