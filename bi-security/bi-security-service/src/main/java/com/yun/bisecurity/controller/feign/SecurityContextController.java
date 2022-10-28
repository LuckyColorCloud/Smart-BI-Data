package com.yun.bisecurity.controller.feign;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.exception.LoopAuthLoginException;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.session.model.TokenModel;
import com.yun.bidatacommon.security.UserSessionInfo;
import com.yun.bidatacommon.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 鉴权提供的内部接口
 *
 * @author Sober
 */
@RestController
@RequestMapping("/open/security/api/context")
@Api(tags = "用户全局会话上下文管理相关接口")
public class SecurityContextController {

    /**
     * abac鉴权接口
     * @author Sober
     * @return com.yun.bidatacommon.vo.Result<java.lang.Boolean>
     */
    @ApiOperation("abac鉴权接口")
    @PostMapping("/checkAbAc")
    public Result<UserSessionInfo> checkAbAc(@RequestParam String route, @RequestParam String method) {
        /**
         * 暂时方案
         * 网关拦截所以请求，通过调用此接口校验。
         * 若接口无需拦截则 LoopAuthAbac.check不会抛出异常，但context无法加载，
         * 但 LoopAuthSession.getTokenModel会抛出非法访问异常，
         * 如有异常 则 拦截异常返回空会话信息。代表此接口无需拦截。
         */
        // 执行abac鉴权
        LoopAuthAbac.check(route,method);
        // 加载会话信息
        try {
            // 获取异常
            TokenModel tokenModel = LoopAuthSession.getTokenModel();
            UserSessionInfo userSessionInfo = new UserSessionInfo();
            userSessionInfo.setToken(tokenModel.getValue());
            userSessionInfo.setLoginId(tokenModel.getLoginId());
            return Result.OK(userSessionInfo);
        } catch (LoopAuthLoginException e) {
            return Result.OK();
        }
    }
}
