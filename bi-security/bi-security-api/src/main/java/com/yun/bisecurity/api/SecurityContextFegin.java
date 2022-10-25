package com.yun.bisecurity.api;

import com.sobercoding.loopauth.abac.model.Policy;
import com.yun.bidataconnmon.vo.Result;
import feign.Param;
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
public interface SecurityContextFegin {

    /**
     * 获取abac鉴权的规则
     * @author Sober
     * @param route 路由
     * @param mode 请求方式
     * @return com.yun.bidataconnmon.vo.Result<java.util.Set<com.sobercoding.loopauth.abac.model.Policy>>
     */
    @RequestMapping(value = "/getPolicySet", method = RequestMethod.POST)
    Result<Set<Policy>> getPolicySet(@RequestParam(value = "route") String route,
                                     @RequestParam("mode") String mode);

    /**
     * 用户鉴权接口
     * @author Sober
     * @param sPID 会话生命周期id
     * @return com.yun.bidataconnmon.vo.Result<java.lang.Boolean>
     */
    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    Result<Boolean> isLogin(@Param(value = "sPID") long sPID);

    /**
     * 获取当前会话LoginId
     * @author Sober
     * @param sPID 会话生命周期id
     * @return com.yun.bidataconnmon.vo.Result<java.lang.String>
     */
    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    Result<String> getLoginId(@Param(value = "sPID") long sPID);
}
