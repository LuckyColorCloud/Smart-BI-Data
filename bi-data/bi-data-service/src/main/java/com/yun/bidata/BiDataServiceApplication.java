package com.yun.bidata;

import lombok.extern.slf4j.Slf4j;
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
@MapperScan(value = "com.yun.bidata.dao")
@ComponentScan(basePackages = {"com.yun.bidata", "com.yun.bidatacommon"})
@EnableFeignClients(basePackages = {
        "com.yun.*.api"
})
public class BiDataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiDataServiceApplication.class, args);
    }
}
