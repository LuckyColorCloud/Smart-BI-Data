package com.yun.bigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.alibaba.druid", "com.yun.bigateway", "com.yun.bidatacommon.*"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
                "com.yun.bidatacommon.config.MyBatisPlusMetaObjectHandler"})
})
@EnableFeignClients
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
