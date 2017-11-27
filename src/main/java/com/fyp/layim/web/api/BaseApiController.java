package com.fyp.layim.web.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

public class BaseApiController {
    /**
     * 获取当前用户ID
     * 这个用户ID是通过token赋值的
     * */
    protected Long getUserId(HttpServletRequest request){
        Object userId = request.getAttribute("LAYIM_UID");
        if(userId != null){
            return Long.parseLong(userId.toString());
        }
        return 0L;
    }
}
