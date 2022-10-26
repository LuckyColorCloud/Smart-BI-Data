package com.yun.bisecurity.config;

import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.model.builder.AbacPolicyFunBuilder;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.api.SecurityContextFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sober
 */
@Configuration
public class LoopAuthConfig {


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
                                    Boolean isLogin = (Boolean) rule;
                                    // 需要登录才验证是否登录
                                    if (isLogin) {
                                        LoopAuthSession.isLogin();
                                    }
                                })
                                // 获得value方式
                                .setSupplierMap(() -> true)
                )
                // loginId 校验规则
                .loginId()
                .setLoginId(() -> LoopAuthSession.getTokenModel().getLoginId())
                .build();
    }


}
