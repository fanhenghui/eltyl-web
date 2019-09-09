package com.eltyl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "eltyl.security")
public class SecurityProperties {

    private String signOutUrl = "/logout.html";

    /**
     * 验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    /**
     * 记住我的有效时间秒
     */
    private int rememberMeSeconds = 60 * 60 * 24 * 7;

    /**
     * session配置过期和并发登录
     */
    private SessionProperties session = new SessionProperties();
}
