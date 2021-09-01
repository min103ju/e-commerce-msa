package com.citizen.userservice.service;

import com.citizen.userservice.dto.UserDto;
import com.citizen.userservice.jpa.UserEntity;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

}
