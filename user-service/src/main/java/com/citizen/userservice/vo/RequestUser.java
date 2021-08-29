package com.citizen.userservice.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {

    @NotNull(message = "Email can't be null")
    @Size(min = 2, message = "Email not be less than two characters")
    private String email;

    @NotNull(message = "Name can't be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "Password can't be null")
    @Size(min = 2, message = "Password must be equal or grater than 8 characters")
    private String pwd;

}
