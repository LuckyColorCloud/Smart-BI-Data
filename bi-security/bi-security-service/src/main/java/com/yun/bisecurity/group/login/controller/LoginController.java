package com.yun.bisecurity.group.login.controller;

import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.model.vo.Result;


import com.yun.bisecurity.group.login.face.LoginFace;
import com.yun.bisecurity.group.login.model.param.LoginParam;
import com.yun.bisecurity.group.login.model.vo.LoginInfoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sober
 */
@Slf4j
@Tag(name = "会话相关")
@Validated
@RestController
public class LoginController {

    private final LoginFace loginFace;

    LoginController(LoginFace loginFace) {
        this.loginFace = loginFace;
    }

    /**
     * 登录接口
     *
     * @param param 登录参数
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     * @author Sober
     */
    @PostMapping("/login")
    @Parameters({
            @Parameter(name = "email", required = true, description = "邮箱"),
            @Parameter(name = "password", required = true, description = "密码")
    })
    @Operation(summary = "登录接口")
    public Result<LoginInfoVo> register(@ParameterObject @Valid LoginParam param) {
        return loginFace.login(param);
    }


    @GetMapping("/out")
    public Result<String> loginOut() {
        // 注销登录
        LoopAuthSession.logout();
        return Result.OK("注销成功");
    }

}
