package com.fyp.layim;

import com.fyp.layim.common.event.ApplyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author fyp
 * LayimApplication
 * */
@SpringBootApplication
public class LayimApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(LayimApplication.class);

        /**
         * 这里监听增加listener，listener才会触发
         * ApplyListener 是监听好友申请的事件
         * */
        springApplication.addListeners(new ApplyListener());

        springApplication.run(args);
    }
}
