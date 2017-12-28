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
        springApplication.run(args);
    }
}
