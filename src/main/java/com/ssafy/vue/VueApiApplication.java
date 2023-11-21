package com.ssafy.vue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.vue", "com.ssafy.model"})
public class VueApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueApiApplication.class, args);
	}

}
