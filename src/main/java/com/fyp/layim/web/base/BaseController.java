package com.fyp.layim.web.base;

import com.fyp.layim.service.auth.ShiroUtil;

public class BaseController {
    /**
     * 获取当前用户ID
     * */
    protected Long getUserId() {
        return ShiroUtil.getUserId();
    }
}
