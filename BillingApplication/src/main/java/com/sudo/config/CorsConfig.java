package com.sudo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
@Configuration
public class CorsConfig implements WebMvcConfigurer
{
    @Override
            public void addCorsMappings(CorsRegistry registry) {
        System.out.println("COrs Config  is picked up!!");
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(false);
                WebMvcConfigurer.super.addCorsMappings(registry);
            }
}
