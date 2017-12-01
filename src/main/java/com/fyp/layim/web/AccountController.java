package com.fyp.layim.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 这里注意，不要使用 RestController ，由于我习惯性的使用了RestController，导致我半天没有将模板对应到templates文件夹下的html，浪费了时间！！！唉。。。
 * */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @RequestMapping("/login")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getPrincipal();
        if(subject.isAuthenticated()){
            return "redirect:/";
        }
        return"/account/login";
    }

    /**
     * 代码参考：http://www.ityouknow.com/springboot/2017/06/26/springboot-shiro.html
     * */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request) throws Exception{
        request.getSession().removeAttribute("login_msg");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg ;
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
               msg = "account";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                msg = "pwd";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                msg = "code";
            } else {
                msg = "other";
            }
            request.getSession().setAttribute("login_msg",msg);
        }
        // 此方法不处理登录成功,由shiro进行处理
        return "/account/login";
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        return "/account/login";
    }

    @RequestMapping(value = "/reg")
    public String reg(){
        return  "/account/reg";
    }
}
