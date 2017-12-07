package com.fyp.layim.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layim")
public class LayimPageController {
    @RequestMapping("/msgbox")
    public String msgbox(){
        return "/layim/msgbox";
    }
}
