package com.StudaTCC.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.StudaTCC.demo")
public class StudaTccApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudaTccApplication.class, args);
	}
}
