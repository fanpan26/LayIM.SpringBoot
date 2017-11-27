package com.fyp.layim.service.auth;

import com.fyp.layim.common.util.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenVerify {

    private static final Logger logger = LoggerFactory.getLogger(TokenVerify.class);
    /**
     * 校验token
     * 返回用户ID
     * */
    public static String IsValid(String token) {
        logger.info("当前token为：{}", token);
        if (token == null) {
            return null;
        }
        try {
            String decryptToken = AESUtil.decrypt(token);
            if (decryptToken == null) {
                return null;
            }
            String tokenArr[] = decryptToken.split("_");
            if (tokenArr.length == 2) {
                String userId = tokenArr[0];
                logger.info("获取到的用户ID为：{}", userId);
                Long tokenTimestamp = Long.parseLong(tokenArr[1]);

                Long nowTimestamp = System.currentTimeMillis();

                Long authTime = nowTimestamp - tokenTimestamp;
                logger.info("时间差：{}", authTime);
                if (authTime > 2 * 3600 * 1000) {
                    //token时间 大于 2 个小时
                    return null;
                }
                return userId;
            }
        } catch (Exception e) {
            return null;
        }
        return null;

    }
}
