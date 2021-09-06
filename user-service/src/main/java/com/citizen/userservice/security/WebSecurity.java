package com.citizen.userservice.security;

import com.citizen.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
// TODO: 2021-08-29 @Configuration의 경우 다른 쪽에 있는 Bean들보다 우선순위가 앞으로 결정된다.
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    // TODO: 2021-09-07 configure 메소드 중 HttpSecurity를 인자로 하는 메소드는 권한에 관련된 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // TODO: 2021-08-29 html Frame으로 처리된 것을 무시한다. (확인 필요)
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/**")
            // TODO: 2021-09-07 IP에 대한 제한
            .hasIpAddress("121.171.104.33")
            .and()
            // TODO: 2021-09-07 Request 요청에 대한 filter 처리를 위한 Filter 추가
            .addFilter(getAuthenticationFilter());
    }

    // TODO: 2021-09-07 authenticationManager를 설정한 AuthenticationFilter를 반환
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();

        // TODO: 2021-09-07 WebSecurityConfigurerAdapter의 authenticationManager() 메소드를 호출
        authenticationFilter.setAuthenticationManager(authenticationManager());

        return authenticationFilter;
    }

    // TODO: 2021-09-07 configure 메소드 중 AuthenticationManagerBuilder 인자로 하는 메소드는 인증에 관련된 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO: 2021-09-07 사용자 데이터를 조회하여 설정
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
