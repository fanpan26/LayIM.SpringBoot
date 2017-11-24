package com.fyp.layim.service.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LayimCredentialsMatcher extends SimpleCredentialsMatcher {

    private static final Logger logger = LoggerFactory.getLogger(LayimCredentialsMatcher.class);

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String password = String.valueOf(usernamePasswordToken.getPassword());
        logger.info("密码为：{}"+password);
        Object accountCredentials = getCredentials(info);
        logger.info("系统返回值：{}",accountCredentials);
        return equals(password, accountCredentials);
    }
}
