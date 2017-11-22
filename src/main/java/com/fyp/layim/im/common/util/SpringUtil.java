package com.fyp.layim.im.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;
    private static SpringUtil Instance = null;

    public synchronized static SpringUtil init() {
        if (Instance == null) {
            Instance = new SpringUtil();
        }
        return Instance;
    }

    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public synchronized static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
