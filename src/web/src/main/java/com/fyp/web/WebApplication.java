package com.fyp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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

