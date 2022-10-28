package com.yun.bigateway.fegin;

import com.yun.bidatacommon.security.UserSessionInfo;
import com.yun.bidatacommon.vo.Result;
import com.yun.bigateway.config.FeignWebFluxConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 鉴权提供的内部接口的Fegin映射
 * @author Sober
 */
@FeignClient(
        value = "bi-security",
        name = "bi-security",
        path = "/open/security/api/context",
        configuration = FeignWebFluxConfig.class
)
public interface SecurityContextFeign {


    /**
     * abac鉴权接口
     * @author Sober
     * @return com.yun.bidatacommon.vo.Result<java.lang.Boolean>
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @RequestMapping(value = "/checkAbAc", method = RequestMethod.POST)
    Result<UserSessionInfo> checkAbAc(@RequestParam String route, @RequestParam String method);

}
