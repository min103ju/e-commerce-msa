package com.citizen.userservice.controller;

import com.citizen.userservice.config.PropertiesConfig;
import com.citizen.userservice.dto.UserDto;
import com.citizen.userservice.service.UserService;
import com.citizen.userservice.vo.Greeting;
import com.citizen.userservice.vo.RequestUser;
import com.citizen.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class UserController {

    private final Environment env;
    private final PropertiesConfig propertiesConfig;
    private final Greeting greeting;
    private final UserService userService;

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

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.map(userDto, ResponseUser.class));
    }



}
