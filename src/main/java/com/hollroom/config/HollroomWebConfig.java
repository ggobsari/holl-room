package com.hollroom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HollroomWebConfig implements WebMvcConfigurer {
    //정적리소스의 경로를 설정

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //특정path로 요청하면 실제 파일이 저장된 위치를 연결해서 리소스를 가져올 수 있도록 처리
        registry.addResourceHandler("/imageload/**")
                .addResourceLocations("file:///C:/backend23/community_upload/");
    }
}
