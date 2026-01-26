package com.example.sns.config;

import com.example.sns.common.argumentResolver.UserInfoArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // 이 패턴으로 요청시 아래 경로에서 정적 리소스를 찾아 응답  . 내가 url로 접근하고 싶은 path /images/10_1231212312/test.png
                .addResourceLocations("file:///D:\\devJava\\xxSNS\\app_images/");

        registry.addResourceHandler("/favicon.ico") // [http://localhost:8080/favicon.ico]
                .addResourceLocations("classpath:/static/"); // 브라우저 탭에 표시되는 아이콘
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserInfoArgumentResolver());
        // HandlerMethodArgumentResolver를 구현한 커스텀 리졸버 등록
    }
}
