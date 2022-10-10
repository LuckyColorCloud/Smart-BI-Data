package com.yun.biapimanage.apifilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json;charset=UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
