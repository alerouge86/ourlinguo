package com.alerouge.ourlinguo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
//public class ProvaApp extends SpringBootServletInitializer {
	public class Application {

//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ProvaApp.class);
//    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
