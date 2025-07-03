package com.atey.service.impl;

import com.atey.config.UserDetailsImpl;
import com.atey.constant.JwtClaimsConstant;
import com.atey.dto.UserLoginDto;
import com.atey.entity.User;
import com.atey.exception.BaseException;
import com.atey.mapper.UserLoginMapper;
import com.atey.message.LoginMessage;
import com.atey.properties.JwtProperties;
import com.atey.service.UserLoginService;
import com.atey.utils.JwtUtil;
import com.atey.vo.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserLoginImpl extends ServiceImpl<UserLoginMapper, User> implements UserLoginService {

    private final JwtProperties jwtProperties;

    private final AuthenticationManager authenticationManager;

    //security
    @Override
    public UserLoginVo login(UserLoginDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        //生成一个包含账号密码的认证信息
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        //AuthenticationManager校验这个信息，并且返回一个已认证的authentication
        authentication = authenticationManager.authenticate(authentication);
        //将已认证的信息存入上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = null;

        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetailsImpl) {
            userDetails = (UserDetailsImpl) principal;
        }
        User user;
        if (userDetails != null) {
            user = userDetails.getUser();
        }else{
            throw new BaseException(LoginMessage.USER_NOT_EXIST);
        }

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        claims.put(JwtClaimsConstant.TYPE,user.getType());

        String token = JwtUtil.generateJwt(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVo userLoginVo = new UserLoginVo();

        userLoginVo.setUserId(user.getId());
        userLoginVo.setAuthentication(token);

        return userLoginVo;
    }

//    @Override
//    public UserLoginVo login(UserLoginDto dto) {
//
//        String username = dto.getUsername();
//        String password = dto.getPassword();
//
//        //判断用户是否存在
//        User user = lambdaQuery()
//                .eq(StrUtil.isNotBlank(username), User::getUsername, username)
//                .one();
//
//        if(ObjectUtil.isNull(user)) {
//            throw new BaseException(LoginMessage.USER_NOT_EXIST);
//        }
//
//        if(!password.equals(user.getPassword())) {
//            throw new BaseException(LoginMessage.USERNAME_PASSWORD_ERROR);
//        }
//
//        //判断账号是否冻结
//        if(ObjectUtil.equals(user.getStatus(), UserStatusEnum.FAIL.getKey())) {
//            throw new BaseException(LoginMessage.USER_FAIL);
//        }
//
//        //用户名和密码正确，生成token
//        Map<String,Object> claims = new HashMap<>();
//        claims.put(JwtClaimsConstant.USER_ID,user.getId());
//        claims.put(JwtClaimsConstant.TYPE,user.getType());
//
//        String token = JwtUtil.generateJwt(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
//
//        UserLoginVo userLoginVo = new UserLoginVo();
//
//        userLoginVo.setUserId(user.getId());
//        userLoginVo.setAuthentication(token);
//
//        return userLoginVo;
//    }

}
