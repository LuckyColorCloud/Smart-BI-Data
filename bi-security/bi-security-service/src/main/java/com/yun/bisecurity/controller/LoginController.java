package com.yun.bisecurity.controller;

import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.group.account.service.AccountService;


import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;

/**
 * @author Sober
 */
@Slf4j
@Tag(name = "会话相关")
@RestController
public class LoginController {

    @Resource
    private AccountService accountService;

    /**
     * 登录接口
     *
     * @param email
     * @param password
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     * @author Sober
     */
    @PostMapping("/login")
    @Parameters({
            @Parameter(name = "email", required = true, description = "邮箱"),
            @Parameter(name = "password", required = true, description = "密码")
    })
    @Operation(summary = "登录接口")
    public Result<String> register(@RequestParam(value = "email") String email,
                                   @RequestParam(value = "password") String password) {
        return accountService.login(email, password);
    }


    @GetMapping("/out")
    public Result<String> loginOut() {
        // 注销登录
        LoopAuthSession.logout();
        return Result.OK("注销成功");
    }

}
