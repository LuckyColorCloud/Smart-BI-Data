package com.yun.bidatastorage;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
@MapperScan(value = "com.yun.bidatastorage.dao")
@ComponentScan(basePackages = {"com.yun.bidatastorage","com.yun.bidataconnmon"})
public class BiDataStorageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiDataStorageServiceApplication.class, args);
    }

}