package com.hollroom.user.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class VerificationCodeConfig {

    @Value("${nurigo-api-key}")
    private String api_key;

    @Value("${nurigo-api-secret-key}")
    private String api_secret_key;

}
