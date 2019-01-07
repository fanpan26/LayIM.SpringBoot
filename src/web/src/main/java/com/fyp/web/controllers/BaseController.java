package com.fyp.web.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class BaseController {
    protected Long getUserId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if(cookie.getName().equals("layim_uid")){
               return Long.valueOf(cookie.getValue());
            }
        }
        return Long.valueOf(0L);
    }
}
