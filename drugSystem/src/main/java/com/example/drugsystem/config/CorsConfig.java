//package com.example.drugsystem.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8081") // 允许指定源
//                .allowedMethods("*") // 允许所有HTTP方法
//                .allowedHeaders("*") // 允许所有请求头
//                .allowCredentials(true); // 允许携带cookie
//    }
//}
