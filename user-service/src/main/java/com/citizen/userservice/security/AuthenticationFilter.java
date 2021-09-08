package com.citizen.userservice.security;

import com.citizen.userservice.dto.UserDto;
import com.citizen.userservice.service.UserService;
import com.citizen.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;

    public AuthenticationFilter(
        AuthenticationManager authenticationManager,
        UserService userService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    // TODO: 2021-09-09 Authentication 시도 메소드
    // TODO: 2021-09-09 1. 사용자가 login, 즉 Authentication을 시도할 때 제일 먼저 실행되는 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin credential = new ObjectMapper()
                .readValue(request.getInputStream(), RequestLogin.class);

            // TODO: 2021-09-07 현재 Spring Security에서 관리하는 AuthenticationManager에서 Token을 생성하여 인증을 진행
            return getAuthenticationManager().authenticate(
                // TODO: 2021-09-07 UsernamePasswordAuthenticationToken에 email(혹은 id), password, role을 인자로 하여 생성
                new UsernamePasswordAuthenticationToken(
                    credential.getEmail(),
                    credential.getPassword(),
                    new ArrayList<>()
                )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: 2021-09-09 Authentication 성공 메소드
    // TODO: 2021-09-09 loadUserByUsername 이후 Authentication 성공 후 실행되는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {

        String username = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(username);

        String token = Jwts.builder()
            .setSubject(userDetails.getUserId())
            .setExpiration(new Date(System.currentTimeMillis() + Long
                .parseLong(env.getProperty("token.expiration_time"))))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());

    }
}
