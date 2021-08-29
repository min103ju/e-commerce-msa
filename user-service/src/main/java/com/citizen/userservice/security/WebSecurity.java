package com.citizen.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
// TODO: 2021-08-29 @Configuration의 경우 다른 쪽에 있는 Bean들보다 우선순위가 앞으로 결정된다.
@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // TODO: 2021-08-29 html Frame으로 처리된 것을 무시한다. (확인 필요)
            .headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/users/**").permitAll();

    }
}
