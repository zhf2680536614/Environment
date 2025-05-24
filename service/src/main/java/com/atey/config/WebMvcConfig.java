package com.atey.config;

import com.atey.interceptor.JwtUserInterceptor;
import com.atey.interceptor.TypeCheckInterceptor;
import com.atey.json.JacksonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置类 注册Web层相关组件
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
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
                .excludePathPatterns("/api/user/captcha/validator")
                        .order(1);

        registry.addInterceptor(typeCheckInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/manage/login")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/captcha")
                .excludePathPatterns("/api/user/captcha/validator")
                .order(2);
    }

    /**
     * 扩展Spring MVC框架的消息转化器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        //将自己的消息转化器加入容器中
        converters.add(0, converter);
    }
}
