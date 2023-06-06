package com.yun.bimessagecenter.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Yun
 */
@Slf4j
@Configuration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpoint() {
        return new ServerEndpointExporter();
    }

}
