package com.yun.bisecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sober
 */
@RestController
public class TestController {
    @GetMapping("/test/abac")
    public String abac1(){
        return "检测成功";
    }
}
