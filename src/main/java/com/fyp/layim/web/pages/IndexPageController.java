package com.fyp.layim.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPageController {
    /**
     * 社区首页
     * */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * api页面
     * */
    @RequestMapping("/api")
    public String api(){
        return "api";
    }
}
