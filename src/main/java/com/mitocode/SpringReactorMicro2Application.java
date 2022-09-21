package com.mitocode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringReactorMicro2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactorMicro2Application.class, args);
	}
}