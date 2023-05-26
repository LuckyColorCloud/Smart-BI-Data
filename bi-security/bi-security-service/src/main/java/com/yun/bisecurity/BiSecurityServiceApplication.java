package com.yun.bisecurity;

import lombok.extern.slf4j.Slf4j;
//import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
@MapperScan(value = "com.yun.bisecurity.dao")
@ComponentScan(basePackages = {"com.alibaba.druid", "com.yun.bisecurity", "com.yun.bidatacommon"})
@EnableFeignClients(basePackages = {
        "com.yun.*.api"
})
public class BiSecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiSecurityServiceApplication.class, args);
    }

}
