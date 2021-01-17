package com.example.Lab4Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;


@EnableJms
@SpringBootApplication
public class Lab4SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(Lab4SpringApplication.class, args);
	}

}
