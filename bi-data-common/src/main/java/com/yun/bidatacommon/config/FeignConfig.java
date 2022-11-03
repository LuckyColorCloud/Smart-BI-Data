package com.yun.bidatacommon.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 请求拦截器,将原始请求header中数据原样通过feign传输
 *
 * @author Sober
 */
@Slf4j
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            // 从MQ收到消息时需要RPC
            log.info("requestAttributes is null");
            return;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);

                // 跳过 content-length, 防止RPC时参数长度与请求时不一致
                if ("content-length".equalsIgnoreCase(name)) {
                    continue;
                }

                requestTemplate.header(name, values);
            }
        }
    }

}