package com.eltyl.module.system.controller;

import com.eltyl.config.SecurityUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PublicController {
    @RequestMapping("/login")
    public String login(HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken){
            return "system/login";
        }else{
            return "redirect:/";
        }
    }
    @RequestMapping("/")
    public String index(ModelMap model){
        SecurityUser user=(SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("nickname",user.getNickname());
        return "system/index";
    }

    @RequestMapping("/home")
    public String home(HttpServletResponse response){
        return "system/home";
    }

    //应用中获取当前用户的方法
    @RequestMapping("/auth")
    @ResponseBody
    public SecurityUser auth(HttpServletResponse response){
        return (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
