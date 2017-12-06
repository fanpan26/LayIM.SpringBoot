package com.fyp.layim.web.base;

import com.fyp.layim.service.auth.ShiroUtil;

public class BaseController {
    private Long userId;
    /**
     * 获取当前用户ID
     * */
    protected Long getUserId(){
        if(userId == null || userId == 0){
            userId = ShiroUtil.getUserId();
        }
        return userId;
    }
}
