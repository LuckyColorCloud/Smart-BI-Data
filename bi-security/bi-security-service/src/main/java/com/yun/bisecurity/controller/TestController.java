package com.yun.bisecurity.controller;

import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sober
 */
@RestController
public class TestController {

    @GetMapping("/login")
    public String register() {
        // 登录方法
        LoopAuthSession.login("1");
        return "登录成功";
    }

    @GetMapping("/islogin")
    public String isLogin() {
        // 验证是否登录
        LoopAuthSession.isLogin();
        return "已经登录";
    }


    @GetMapping("/out")
    public String loginOut() {
        // 注销登录
        LoopAuthSession.logout();
        return "注销成功";
    }

    @GetMapping("/test/abac")
    public String abac1() {
        return "检测成功";
    }
}
