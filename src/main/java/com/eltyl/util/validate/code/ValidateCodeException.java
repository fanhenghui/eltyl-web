package com.eltyl.util.validate.code;

import lombok.Data;
import org.springframework.security.core.AuthenticationException;
@Data
public class ValidateCodeException extends AuthenticationException {
    private static final long serialVersionUID = -7525757620869234981L;

    private Integer code;

    public ValidateCodeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
