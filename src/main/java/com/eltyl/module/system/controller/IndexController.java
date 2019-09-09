package com.eltyl.module.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    //@PreAuthorize("hasAuthority('Index')")
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    //@PreAuthorize("hasPermission('good_add')")
    @PreAuthorize("hasAnyRole('good:test')")
    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test";
    }

    @PreAuthorize("hasAuthority('good:add')")
    @RequestMapping("/test2")
    @ResponseBody
    public String test2(){
        return "异常";
    }
    @RequestMapping("/haha2")
    @ResponseBody
    public String haha2(){
        return "异常2";
    }

    @RequestMapping("/out")
    @ResponseBody
    public String out(){
        return "退出";
    }
}
