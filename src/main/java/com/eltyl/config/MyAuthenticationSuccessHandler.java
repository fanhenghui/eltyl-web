package com.eltyl.config;

import com.alibaba.fastjson.JSON;
import com.eltyl.mapper.SysUserMapper;
import com.eltyl.module.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //修改用户登录时间
        SecurityUser user=(SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        sysUserService.updateLastLoginTime(user.getId(),new Date().getTime()/1000);
        Map result=new HashMap();
        result.put("code",0);
        result.put("msg","登录成功");
        //根据不同角色跳转
        /*if(user.getRoleCodeList().contains("teacher")){
            result.put("data","/teacher/index.html");
        }else if(user.getRoleCodeList().contains("student")){
            result.put("data","/student/index.html");
        }else if(user.getRoleCodeList().contains("admin")){
            result.put("data","/admin/index.html");
        }*/
        result.put("data","/");
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}

