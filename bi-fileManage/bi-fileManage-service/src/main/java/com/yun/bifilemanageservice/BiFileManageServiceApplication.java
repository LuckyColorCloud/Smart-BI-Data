package com.yun.bifilemanageservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
//@EnableFeignClients(basePackages = {
//        "com.yun.bifilemanageapi.api"
//})
public class BiFileManageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiFileManageServiceApplication.class, args);
    }

}
