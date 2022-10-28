package com.yun.bigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.yun.bigateway", "com.yun.bidatacommon.constant"})
@EnableFeignClients
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
