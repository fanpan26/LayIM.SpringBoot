package com.fyp.layim;

import com.fyp.layim.im.LayimWebsocketStarter;
import com.fyp.layim.im.server.LayimMsgHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author fyp
 * LayimApplication
 * */
@SpringBootApplication
public class LayimApplication {
    public static void main(String[] args){
        SpringApplication.run(LayimApplication.class,args);
    }
}
