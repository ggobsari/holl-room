package com.hollroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class WebSecurityConfig {
    //================================================================================================================//
    @Bean //비밀번호 인코딩 타입 설정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
