package com.fyp.layim.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    /**
     * 用户首页
     * */
    @RequestMapping(value = "/user/{uid}")
    public String home(@PathVariable("uid") String uid){
        return "/user/home";
    }
}
