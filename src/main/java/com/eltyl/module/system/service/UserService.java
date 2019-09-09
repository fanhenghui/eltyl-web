package com.eltyl.module.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    private HttpServletRequest request;

    public void doLogin(){
        String token=(String)request.getAttribute("claims_admin");
        if(StringUtils.isEmpty(token)){
            throw new RuntimeException("权限不足");
        }
    }
}
