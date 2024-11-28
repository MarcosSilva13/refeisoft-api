package com.refeisoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RefeisoftApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefeisoftApiApplication.class, args);
	}

}
