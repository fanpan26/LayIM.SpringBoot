package com.fyp.layim.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 这里注意，不要使用 RestController ，由于我习惯性的使用了RestController，导致我半天没有将模板对应到templates文件夹下的html，浪费了时间！！！唉。。。
 * */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @RequestMapping("/login")
    public String login() {
        return "/account/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String username,String password){
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(token);
            //UserAccount account = (UserAccount) subject.getPrincipal();
            return "index";
        } catch(Exception e) {
            //返回登录页面
            return "login";
        }
    }
}
