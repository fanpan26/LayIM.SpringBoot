package com.fyp.layim.web.pages;

import com.fyp.layim.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    /**
     * 用户首页
     * */
    @RequestMapping(value = "/{uid}")
    public String home(@PathVariable("uid") Long uid, Model model){
        HashMap<String,Object> userMap = new HashMap<>();
        //是否是自己
        boolean isSelf = uid == getUserId();
        userMap.put("self",uid == getUserId());
        model.addAttribute(userMap);
        return "/user/home";
    }
}
