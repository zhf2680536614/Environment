package com.atey.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.atey.constant.JwtClaimsConstant;
import com.atey.context.UserContext;
import com.atey.enumeration.UserTypeEnum;
import com.atey.exception.BaseException;
import com.atey.message.JwtMessage;
import com.atey.properties.JwtProperties;
import com.atey.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUserInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    private final UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String uri = request.getRequestURI();
        String typename = uri.split("/")[2];

        try {
            if (typename.equals(UserTypeEnum.USER.getValue())) {

                String token = request.getHeader(jwtProperties.getUserTokenName());
                if (ObjectUtil.isEmpty(token)) {
                    throw new BaseException(JwtMessage.JWT_NULL);
                }

                //当前登录的是用户
                log.info("用户jwt校验{}", token);
                Claims claims = JwtUtil.parseJwt(jwtProperties.getUserSecretKey(), token);
                Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
                log.info("当前用户的id为{}", userId);
                Map<Long, String> map = new HashMap<>();
                map.put(userId, UserTypeEnum.USER.getValue());
                userContext.setUser(map);
            } else {

                String token = request.getHeader(jwtProperties.getManageTokenName());
                if (ObjectUtil.isEmpty(token)) {
                    throw new BaseException(JwtMessage.JWT_NULL);
                }

                log.info("管理员jwt校验{}", token);
                Claims claims = JwtUtil.parseJwt(jwtProperties.getManageSecretKey(), token);
                Long userId = Long.valueOf(claims.get(JwtClaimsConstant.MANAGE_ID).toString());
                log.info("当前管理员的id为{}", userId);
                Map<Long, String> map = new HashMap<>();
                map.put(userId, UserTypeEnum.MANAGE.getValue());
                userContext.setUser(map);
            }
            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userContext.clearUser();
    }
}
