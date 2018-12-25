package com.fyp.web.controllers;

import com.fyp.entity.result.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    /**
     * 登录获取Token
     * */
    @PostMapping("login")
    public JsonResult Login(String name,String password){
        return JsonResult.success(com.fyp.utils.jwt.JwtUtil.createToken(Long.valueOf(203328)));
    }
}
