package com.eltyl.module.system.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PublicController {
    @RequestMapping("/login")
    public String login(HttpServletResponse response) throws IOException {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //if(auth instanceof AnonymousAuthenticationToken){
        System.out.println("in login page");
            return "login";
        /*}else{
            return "redirect:/login";
            response.sendRedirect("/");
            return "";
        }*/
    }
}
