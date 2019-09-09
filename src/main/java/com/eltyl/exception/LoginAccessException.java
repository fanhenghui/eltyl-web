package com.eltyl.exception;

import org.springframework.security.access.AccessDeniedException;

public class LoginAccessException extends AccessDeniedException {
    public LoginAccessException(String msg) {
        super(msg);
    }

    public LoginAccessException(String msg, Throwable t) {
        super(msg, t);
    }
}
