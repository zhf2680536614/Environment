package com.atey.config.security;

import com.atey.entity.Authority;
import com.atey.entity.RAConfig;
import com.atey.entity.RoleAuthorities;
import com.atey.enumeration.RoleAuthorityEnum;
import com.atey.filter.JwtAuthenticationTokenFilter;
import com.atey.mapper.RoleAuthorityMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity //此注解开启 Security 的 Web 安全功能
@RequiredArgsConstructor
public class SecurityConfig {

    private final RoleAuthorityMapper roleAuthorityMapper;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<RoleAuthorities> roleAuthorities = getRoleAuthorities();
        List<String> notAuthorities = getNotAuthorities();
        http
                // 开启授权保护
                .authorizeHttpRequests((authorize) -> {
                            authorize
                                    // 不需要认证的地址有哪些
                                    .requestMatchers(notAuthorities.toArray(new String[0])).permitAll();
                            //基于角色的访问控制
                            roleAuthorities.forEach(ra -> {
                                authorize.requestMatchers(ra.getAuthorities().toArray(new String[0])).hasRole(ra.getRole());
                            });

                            authorize// 对所有请求开启授权保护
                                    .anyRequest();
                            //已认证的请求会被自动授权
                            //.authenticated();
                        }
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//基于token不需要session
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 认证成功和认证失败的处理逻辑
                .formLogin((form) -> {
                            form.successHandler(new MyAuthenticationSuccessHandle());
                            form.failureHandler(new MyAuthenticationFailureHandle()).permitAll();
                        }
                );

        // 启用“记住我”功能的。允许用户在关闭浏览器后，仍然保持登录状态，直到他们主动注销或超出设定的过期时间。
        //.rememberMe(Customizer.withDefaults());
        // 关闭 csrf CSRF（跨站请求伪造）是一种网络攻击，攻击者通过欺骗已登录用户，诱使他们在不知情的情况下向受信任的网站发送请求。
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    //获取需要认证的角色与权限信息
    public List<RoleAuthorities> getRoleAuthorities() {
        List<RAConfig> roleAuthorities = roleAuthorityMapper.query(RoleAuthorityEnum.NORMAL.getKey());
        if (roleAuthorities.isEmpty()) {
            return null;
        }
        Map<String, List<String>> collect = roleAuthorities.stream()
                .collect(Collectors.groupingBy(
                        RAConfig::getRole,
                        Collectors.mapping(RAConfig::getAuthority, Collectors.toList())
                ));
        return collect.entrySet().stream()
                .map(entry -> new RoleAuthorities(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    //获取需要认证的角色与权限信息
    public List<String> getNotAuthorities() {
        return Db.lambdaQuery(Authority.class)
                .eq(Authority::getAuth, RoleAuthorityEnum.FAIL.getKey())
                .list().stream().map(Authority::getName).collect(Collectors.toList());
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
