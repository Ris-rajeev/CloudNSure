package com.realnet.loggingeachuserconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingConfiguration implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor userAccessLogInterceptor() {
        return new UserAccessLogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAccessLogInterceptor());
    }
}




