package com.atey.filter;

import com.atey.config.security.UserDetailsImpl;
import com.atey.constant.JwtClaimsConstant;
import com.atey.context.UserContext;
import com.atey.entity.Role;
import com.atey.entity.User;
import com.atey.entity.UserRole;
import com.atey.enumeration.RoleAuthorityEnum;
import com.atey.enumeration.UserTypeEnum;
import com.atey.properties.JwtProperties;
import com.atey.utils.JwtUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;

    private final UserContext userContext;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String authorization = request.getHeader("Authentication");
        //当前登录的是用户
        log.info("用户jwt校验{}", authorization);
        Claims claims = JwtUtil.parseJwt(jwtProperties.getUserSecretKey(), authorization);
        Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
        log.info("当前用户的id为{}", userId);
        Map<Long, String> map = new HashMap<>();
        map.put(userId, UserTypeEnum.USER.getValue());
        userContext.setUser(map);

        User user = Db.lambdaQuery(User.class)
                .eq(User::getId, userId)
                .one();

        UserDetailsImpl loginUser = new UserDetailsImpl();
        loginUser.setUser(user);

        List<UserRole> list = Db.lambdaQuery(UserRole.class)
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getStatus, RoleAuthorityEnum.NORMAL.getKey())
                .list();
        List<Long> roleIdList = list.stream().map(UserRole::getRoleId).toList();

        List<String> roleNameList = Db.lambdaQuery(Role.class)
                .in(Role::getId, roleIdList)
                .eq(Role::getStatus, RoleAuthorityEnum.NORMAL.getKey())
                .list()
                .stream()
                .map(Role::getName)
                .toList();

        List<GrantedAuthority> authorities = roleNameList.stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
        //如果是有效的jwt，那么设置该用户为认证后的用户
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
