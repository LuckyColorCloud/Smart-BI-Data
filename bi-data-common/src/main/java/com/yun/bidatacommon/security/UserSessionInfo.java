package com.yun.bidatacommon.security;

import cn.hutool.json.JSONUtil;
import com.yun.bidatacommon.constant.SecurityConfig;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author Sober
 */
@Data
public class UserSessionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;

    /**
     * 登录Id
     */
    private String loginId;

    public static UserSessionInfo get() {
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return JSONUtil.toBean(request.getHeader(SecurityConfig.getSessionName()) , UserSessionInfo.class);
    }

    public String toJson() {
        return "{" +
                "\"token\":\"" + token +  "\"," +
                "\"loginId\":\"" + loginId +
                "\"}";
    }
}
