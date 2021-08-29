package com.citizen.userservice;

import com.citizen.userservice.config.PropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableDiscoveryClient
// TODO: 2021-08-18 Eureka Client임을 설정
// TODO: 2021-08-18 @EnableDiscoveryClient와 EnableEurekaClient의 차이 확인
@EnableConfigurationProperties(PropertiesConfig.class)
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	// TODO: 2021-08-29 BCryptPasswordEncoder를 Applicaton이 구동되면서 BCryptPasswordEncoder를 Bean으로 등록하기 위함
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
