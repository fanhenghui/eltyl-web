package com.eltyl.config;

import com.eltyl.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    // 目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        String header=request.getHeader("Authorization");
        if(!StringUtils.isEmpty(header)){
            if(header.startsWith("Bearer ")){
                String token=header.substring(7);
                //验证令牌
                try{
                    Claims claims=jwtUtil.parseJWT(token);
                    String roles=(String)claims.get("roles");
                    if (roles != null & roles.equals("admin")) {
                        request.setAttribute("claims_admin",token);
                    }
                    if (roles != null & roles.equals("user")) {
                        request.setAttribute("claims_user",token);
                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌不正确");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
