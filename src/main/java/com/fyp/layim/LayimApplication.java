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
       try {
            new LayimWebsocketStarter(8081, new LayimMsgHandler()).start();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}
