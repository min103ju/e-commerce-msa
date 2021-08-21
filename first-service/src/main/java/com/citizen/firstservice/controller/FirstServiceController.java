package com.citizen.firstservice.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/first-service")
@RestController
public class FirstServiceController {

    // TODO: 2021-08-22 application.yml에 정의된 속성 정보를 조회하는 객체
    Environment env;

    public FirstServiceController(Environment env) {
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the First Service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello World in First Service";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server port={}", request.getServerPort());
        return String.format("Hi, there. This is a message from First Service on Port %s.",
            // TODO: 2021-08-22 application.yml에 random port를 조회
            // TODO: 2021-08-22 실제 할당되어진 port
            env.getProperty("local.server.port"));

        // 52924

        // 52944
    }

}
