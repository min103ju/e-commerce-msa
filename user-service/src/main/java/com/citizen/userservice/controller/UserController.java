package com.citizen.userservice.controller;

import com.citizen.userservice.config.PropertiesConfig;
import com.citizen.userservice.dto.UserDto;
import com.citizen.userservice.jpa.UserEntity;
import com.citizen.userservice.service.UserService;
import com.citizen.userservice.vo.Greeting;
import com.citizen.userservice.vo.RequestUser;
import com.citizen.userservice.vo.ResponseUser;
import com.google.inject.internal.cglib.core.$Local;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final Environment env;
    private final PropertiesConfig propertiesConfig;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health-check")
    public String status() {
        return String.format("It's Working in User Service on Port %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("env : ");
        sb.append(env.getProperty("greeting.message") + ", ");
        sb.append("propertiesConfig : ");
        sb.append(propertiesConfig.getMessage() + ", ");
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

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUser.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {

        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }

}
