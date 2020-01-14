package com.fyp.web.controllers;

import com.fyp.entity.result.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class AccountController extends  BaseController{

    /**
     * 登录获取Token
     * */
    @GetMapping("token")
    public JsonResult getToken(HttpServletRequest request) {
        Long userId = getUserId(request);
        if (userId > 0) {
            return JsonResult.success(com.fyp.utils.jwt.JwtUtil.createToken(userId));
        }
        return JsonResult.fail("no login");
    }

    /**
     * 模拟用户登录，写入cookie
     * */
    @GetMapping("login")
    public JsonResult login(String userId, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("layim_uid", userId);
        cookie.setMaxAge(7200);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/");
        return JsonResult.success();
    }
}
