package com.yun.bimessagecenter;

import com.yun.bimessagecenter.service.SocketConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Yun
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.yun.bimessagecenter", "com.yun.bidatacommon"})
public class BiMessageCenterServiceApplication implements InitializingBean {
    public static void main(String[] args) {
        SpringApplication.run(BiMessageCenterServiceApplication.class, args);
    }


    @Autowired
    SocketConfigService socketConfigService;

    @Override
    public void afterPropertiesSet() throws Exception {
        socketConfigService.init();
    }

}
