package com.fyp.im.utils;

import com.fyp.service.intf.LayIMService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class BeanUtil {

    private static ApplicationContext context;

    private static ApplicationContext getApplicationContext(){
        if (context == null){
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return context;
    }

    public static Object getBean(String beanName){
        return getApplicationContext().getBean(beanName);
    }

    public static void main(String[] args) {
        LayIMService bean = (LayIMService) getBean("layIMService");
        List<Long> groupids = bean.getGroupIds(203328L);
        System.out.println(groupids);
    }
}
