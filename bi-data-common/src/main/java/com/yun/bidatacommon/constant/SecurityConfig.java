package com.yun.bidatacommon.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.io.Serializable;

/**
 * @author Sober
 */
@Component
@ConfigurationProperties(prefix = "bi.security")
public class SecurityConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求头中的会话信息名称
     */
    private static String sessionName = "userSessionInfo";

    public static String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        SecurityConfig.sessionName = sessionName;
    }
}
