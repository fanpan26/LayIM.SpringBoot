package com.fyp.layim;

import com.fyp.layim.common.event.application.AddFriendListener;
import com.fyp.layim.common.event.application.ApplyListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        springApplication.addListeners(new AddFriendListener());

        springApplication.run(args);
    }
}
