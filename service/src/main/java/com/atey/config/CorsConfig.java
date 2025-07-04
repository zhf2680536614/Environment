package com.atey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://www.yyatey.xin:8081"); // 允许前端的域名
        config.addAllowedOrigin("http://8.130.134.127:8081"); // 允许前端的域名
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*"); // 允许所有的请求头
        config.addAllowedMethod("*"); // 允许所有的请求方法
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
