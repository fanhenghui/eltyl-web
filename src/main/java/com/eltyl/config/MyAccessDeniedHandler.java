package com.eltyl.config;

import com.alibaba.fastjson.JSON;
import com.eltyl.util.HttpUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        if (HttpUtil.isAjaxRequest(request)) {// AJAX请求,使用response发送403
            Map<String,Object> responseBody = new HashMap<>();
            responseBody.put("code",403);
            responseBody.put("msg","无权访问该页面");
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(responseBody));
        } else if (!response.isCommitted()) {// 非AJAX请求，跳转系统默认的403错误界面，在web.xml中配置
            response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
        }
    }
}
