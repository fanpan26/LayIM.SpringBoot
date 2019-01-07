package com.fyp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.fyp.**"})
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
		try {
			com.fyp.im.ServerStarter.start();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

}

