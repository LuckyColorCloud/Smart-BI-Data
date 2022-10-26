package com.yun.bisecurity.controller.feign;

import com.sobercoding.loopauth.abac.carryout.LoopAuthAbac;
import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.api.SecurityContextFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 鉴权提供的内部接口
 *
 * @author Sober
 */
@RestController
@RequestMapping("/open/security/api/context")
@Api(tags = "用户全局会话上下文管理相关接口")
public class SecurityContextController implements SecurityContextFeign {

    /**
     * abac鉴权接口
     * @author Sober
     * @return com.yun.bidatacommon.vo.Result<java.lang.Boolean>
     */
    @ApiOperation("abac鉴权接口")
    @Override
    public Result<Boolean> checkAbAc(String route, String method) {
        // 执行abac鉴权
        LoopAuthAbac.check(route,method);
        return Result.OK(true);
    }
}
