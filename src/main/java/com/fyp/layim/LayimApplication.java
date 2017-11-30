package com.fyp.layim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author fyp
 * LayimApplication
 * */
@SpringBootApplication
public class LayimApplication {
    public static void main(String[] args) {
       ApplicationContext applicationContext = SpringApplication.run(LayimApplication.class, args);
    }
}
