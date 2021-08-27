package com.citizen.userservice.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Greeting {

    // TODO: 2021-08-27 @Value를 통해 application.yml의 속성 값을 가져올때는 ${} 형태로 가져와야한다.
    @Value("${greeting.message}")
    private String message;
}
