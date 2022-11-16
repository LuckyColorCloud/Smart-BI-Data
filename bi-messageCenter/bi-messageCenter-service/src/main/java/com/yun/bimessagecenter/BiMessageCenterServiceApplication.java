package com.yun.bimessagecenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.yun.bimessagecenter","com.yun.bidatacommon"})
public class BiMessageCenterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiMessageCenterServiceApplication.class, args);
    }

}
