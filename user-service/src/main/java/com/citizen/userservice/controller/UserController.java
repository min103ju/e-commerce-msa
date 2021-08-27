package com.citizen.userservice.controller;

import com.citizen.userservice.config.PropertiesConfig;
import com.citizen.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UserController {

    private final Environment env;
    private final PropertiesConfig propertiesConfig;
    private final Greeting greeting;

    @GetMapping("/health-check")
    public String status() {
        return "It's Working in User Service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("env : ");
        sb.append(propertiesConfig.getMessage() + ", ");
        sb.append("propertiesConfig : ");
        sb.append(env.getProperty("greeting.message") + ", ");
        sb.append("greeting component : ");
        sb.append(greeting.getMessage());
        return sb.toString();
    }



}
