package com.fyp.layim.web.filter;

import com.fyp.layim.im.packet.ContextUser;
import com.fyp.layim.im.packet.LayimContextUserInfo;
import com.fyp.layim.service.UserService;
import com.fyp.layim.service.auth.ShiroUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LayimAspect {
    private final Logger logger = LoggerFactory.getLogger(LayimAspect.class);

    @Autowired
    private UserService userService;

    private void setUserInfo(HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            Long userId = ShiroUtil.getUserId();
            if(userId>0) {
                LayimContextUserInfo contextUserInfo = userService.getContextUserInfo(userId);
                ContextUser contextUser = contextUserInfo.getContextUser();
                request.getSession().setAttribute("user",contextUser);
            }
        }
    }


    @Pointcut("execution(public * com.fyp.layim.web.*.*(..))")
    public  void log(){}

    @Pointcut("execution(public * com.fyp.layim.web.pages.*.*(..))")
    public void user(){}

    @Before("user()")
    public void beforeRunPage(){
        logger.info("进入需要设置用户信息的URL");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        setUserInfo(request);
    }

    @Before("log()")
    public void before(JoinPoint joinPoint){
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
//        logger.info("HTTP_METHOD : " + request.getMethod());
//        logger.info("IP : " + request.getRemoteAddr());
//        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }
}
