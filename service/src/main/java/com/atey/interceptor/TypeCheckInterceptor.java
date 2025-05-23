package com.atey.interceptor;

import com.atey.context.UserContext;
import com.atey.enumeration.UserTypeEnum;
import com.atey.exception.BaseException;
import com.atey.message.TypeCheckMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Set;

/**
 * 权限校验
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TypeCheckInterceptor implements HandlerInterceptor {

    private final UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断拦截到的是Controller的方法还是其他资源
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }

        String uri = request.getRequestURI();
        String typename = uri.split("/")[2];

        //当前登录用户的类型
        Map<Long, String> user = userContext.getUser();

        Set<Map.Entry<Long, String>> entries = user.entrySet();

        String loginType = "";
        for (Map.Entry<Long, String> entry : entries) {
            loginType = entry.getValue();
        }

        if(!loginType.equals(typename)) {
            throw new BaseException(TypeCheckMessage.TYPE_NOT_EQUALS);
        }

        return true;
    }
}
