package com.eltyl.util.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 */
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     * @param request
     * @return
     */
    ValidateCode generate(ServletWebRequest request);
}
