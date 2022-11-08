package com.yun.bidatacommon.config;

import com.yomahub.tlog.resttemplate.TLogRestTemplateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Tlog配置类
 *
 * @author Yun
 */
@Configuration
public class TLogConfig {

    @Bean
    public RestTemplate initRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new TLogRestTemplateInterceptor()));
        return restTemplate;
    }
}
