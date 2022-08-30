package com.yun.biapimanage.apiFilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Yun
 */
@Slf4j
@Component
public class ApiAuthFilter implements Filter {
    /**
     * 拦截 smart.api.context 路径 这里可以做动态接口的 权限操作 字段权限等
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

    }
}
