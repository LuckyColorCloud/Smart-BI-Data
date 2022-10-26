package com.yun.bisecurity.api;

import com.sobercoding.loopauth.abac.model.Policy;
import com.yun.bidatacommon.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * 鉴权提供的内部接口的Fegin映射
 * @author Sober
 */
@FeignClient(
        value = "bi-security",
        name = "bi-security",
        path = "/open/security/api/context"
)
public interface SecurityContextFeign {


    /**
     * abac鉴权接口
     * @author Sober
     * @return com.yun.bidatacommon.vo.Result<java.lang.Boolean>
     */
    @RequestMapping(value = "/checkAbAc", method = RequestMethod.GET)
    Result<Boolean> checkAbAc(@RequestParam String route,@RequestParam String method);

}
