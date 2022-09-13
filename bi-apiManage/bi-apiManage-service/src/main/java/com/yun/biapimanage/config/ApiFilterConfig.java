package com.yun.biapimanage.config;

import com.yun.biapimanage.apiFilter.ApiAuthFilter;
import com.yun.biapimanage.apiFilter.ApiServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Yun
 */
@Component
public class ApiFilterConfig {

    @Value("${smart.api.context}")
    String apiContext;

    @Autowired
    ApiAuthFilter apiAuthFilter;
    @Autowired
    ApiServlet apiServlet;

    /**
     * 路径权限验证 等
     *
     * @return
     */
    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public FilterRegistrationBean authFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(apiAuthFilter);
        registrationBean.addUrlPatterns(String.format("/%s/*", apiContext));
        registrationBean.setOrder(2);
        registrationBean.setEnabled(true);
        return registrationBean;
    }

    /**
     * 拦截web请求
     *
     * @return
     */

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ServletRegistrationBean getServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(apiServlet);
        bean.addUrlMappings(String.format("/%s/*", apiContext));
        return bean;
    }
}
