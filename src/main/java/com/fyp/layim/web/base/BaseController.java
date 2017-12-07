package com.fyp.layim.web.base;

import com.fyp.layim.im.packet.ContextUser;
import com.fyp.layim.service.auth.ShiroUtil;

public class BaseController {
    /**
     * 获取当前用户ID
     * */
    protected Long getUserId() {
        return ShiroUtil.getUserId();
    }

    protected ContextUser getContextUser(){
        return ShiroUtil.getCurrentUser();
    }
}
