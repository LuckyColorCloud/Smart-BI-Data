package com.yun.bisecurity.controller;

import com.sobercoding.loopauth.abac.model.Policy;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.yun.bidataconnmon.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class SecurityContextController {

    /**
     * 获取abac鉴权的规则
     *
     * @param route 路由
     * @param mode  请求方式
     * @return com.yun.bidataconnmon.vo.Result<java.util.Set < com.sobercoding.loopauth.abac.model.Policy>>
     * @author Sober
     */
    @PostMapping("/getPolicySet")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "route", dataType = "String", required = true, value = "路由"),
            @ApiImplicitParam(paramType = "query", name = "mode", dataType = "String", required = true, value = "请求方法")
    })
    @ApiOperation("获取abac鉴权的规则")
    public Result<Set<Policy>> getPolicySet(@Param("route") String route, @Param("mode") String mode) {
        LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(mode);
        // 这里只做演示，自行编写的时候，请根据自己存储abac规则的方式查询获取
        Set<Policy> set = new HashSet<>();
        // 根据路由地址及请求方式查询 插入
        if (route.equals("/bi-security/test/abac") && loopAuthHttpMode.equals(LoopAuthHttpMode.GET)) {
            set.add(new Policy()
                    // 规则名称
                    .setName("test")
                    // 规则中的属性名称 及 属性值 用于后续进行 规则匹配校验
                    .setProperty("loginId", "2")
            );
        }
        return Result.OK(set);
    }

    /**
     * 用户鉴权接口
     *
     * @param sPid 会话生命周期id
     * @return com.yun.bidataconnmon.vo.Result<java.lang.Boolean>
     * @author Sober
     */
    @PostMapping("/isLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sPID", dataType = "long", required = true, value = "会话生命周期id")
    })
    @ApiOperation("用户鉴权接口")
    public Result<Boolean> isLogin(@Param("sPID") long sPid) {
        return Result.OK(true);
    }

    /**
     * 获取当前会话LoginId
     *
     * @param sPid 会话生命周期id
     * @return com.yun.bidataconnmon.vo.Result<java.lang.String>
     * @author Sober
     */
    @PostMapping("/getLoginId")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sPid", dataType = "long", required = true, value = "会话生命周期id")
    })
    @ApiOperation("获取当前会话LoginId")
    public Result<String> getLoginId(@Param("sPID") long sPid) {
        return Result.OK("1");
    }

}
