package com.citizen.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;

@SpringBootApplication
// TODO: 2021-08-18 Eureka Client임을 설정 
// TODO: 2021-08-18 @EnableDiscoveryClient와 EnableEurekaClient의 차이 확인
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
