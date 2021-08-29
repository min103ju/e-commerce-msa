package com.citizen.userservice.service;

import com.citizen.userservice.dto.UserDto;
import com.citizen.userservice.jpa.UserEntity;
import com.citizen.userservice.jpa.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // TODO: 2021-08-29 Password를 해싱하기 위해 Bcrypt 알고리즘을 사용
    // TODO: 2021-08-29 Random salt를 부여하여 여러번  Hash를 적용한 암호화 방식
    // TODO: 2021-08-29 BCryptPasswordEncoder는 Bean으로 등록되어 있지 않다.
    // TODO: 2021-08-29 따라서, 가장 먼저 Bean으로 등록되는 Class에서 관리하여 Bean으로 등록될 수 있도록 한다.
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
            // TODO: 2021-08-29 형변환을 할때의 전략을 설정
            // TODO: 2021-08-29 STRICT -> 확실하게 매칭될때 형변환 가능하도록 설정
            .setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

}
