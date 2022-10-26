package com.yun.bisecurity.controller;

import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.yun.bisecurity.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Sober
 */
@Slf4j
@Api(tags = "会话相关")
@RestController
public class LoginController {

    @Resource
    private AccountService accountService;

    /**
     * 登录接口
     * @author Sober
     * @param email
     * @param password
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     */
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "email", dataType = "String", required = true, value = "邮箱"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")
    })
    @ApiOperation("登录接口")
    public Result<Object> register(@RequestParam(value = "email") String email,
                           @RequestParam(value = "password") String password) {
        return accountService.login(email, password);
    }


    @GetMapping("/out")
    public Result<Object> loginOut() {
        // 注销登录
        LoopAuthSession.logout();
        return Result.OK("注销成功");
    }

    @GetMapping("/test/abac")
    public String abac1() {
        return "检测成功";
    }
}
