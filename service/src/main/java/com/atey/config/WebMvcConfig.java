package com.atey.config;

import com.atey.interceptor.JwtUserInterceptor;
import com.atey.interceptor.TypeCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类 注册Web层相关组件
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private final JwtUserInterceptor jwtUserInterceptor;

    private final TypeCheckInterceptor typeCheckInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtUserInterceptor)
                .addPathPatterns("/api/user/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/captcha")
                .addPathPatterns("/api/manage/**")
                .excludePathPatterns("/api/manage/login")
//                .excludePathPatterns("/api/user/captcha/validator")
                        .order(1);

        registry.addInterceptor(typeCheckInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/manage/login")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/captcha")
//                .excludePathPatterns("/api/user/captcha/validator")
                .order(2);
    }
}
