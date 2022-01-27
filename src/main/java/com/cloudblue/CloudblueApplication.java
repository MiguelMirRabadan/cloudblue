package com.cloudblue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudblueApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudblueApplication.class, args);
	}

}
