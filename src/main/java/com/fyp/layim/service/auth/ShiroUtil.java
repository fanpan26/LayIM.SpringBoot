package com.fyp.layim.service.auth;

import com.fyp.layim.domain.UserAccount;
import com.fyp.layim.im.packet.ContextUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroUtil {
    private static Logger logger = LoggerFactory.getLogger(ShiroUtil.class);
    /**
     * 从shiro中拿用户ID
     * */
    public static Long getUserId(){
        Subject subject = SecurityUtils.getSubject();
        Long userId = 0L;
        if(subject.isAuthenticated()) {
            UserAccount userAccount = (UserAccount) subject.getPrincipal();
            userId = userAccount.getId();
        }
        logger.info("当前获取到的用户ID：{}",userId);
        return userId;
    }

    /**
     * 获取当前用户信息
     * */
    public static ContextUser getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        return (ContextUser)subject.getSession().getAttribute("user");
    }
}
