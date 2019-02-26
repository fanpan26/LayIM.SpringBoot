package com.fyp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/chatlog")
    public String chatlog() {
        return "chatlog";
    }
}
