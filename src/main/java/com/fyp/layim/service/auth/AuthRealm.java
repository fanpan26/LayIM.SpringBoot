package com.fyp.layim.service.auth;

import com.fyp.layim.domain.UserAccount;
import com.fyp.layim.im.common.util.SpringUtil;
import com.fyp.layim.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 用户登录验证逻辑，用 shiro 框架
 * */
@Configuration
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initCredentialsMatcher(){
       setCredentialsMatcher(new LayimCredentialsMatcher());
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (userService == null) {
            userService = (UserService) SpringUtil.getBean("userService");
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        //查询
        UserAccount account = userService.getUserAccount(username);
        if (account != null) {
            return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
        }
        return null;
    }

    /**
     * 暂时先不做权限，只做有没有登录
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /*
        *  User user=(User) principal.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
        List<String> permissions=new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(roles.size()>0) {
            for(Role role : roles) {
                Set<Module> modules = role.getModules();
                if(modules.size()>0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
        * */
        return null;
    }
}
