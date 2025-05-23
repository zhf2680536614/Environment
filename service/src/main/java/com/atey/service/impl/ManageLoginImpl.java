package com.atey.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atey.constant.JwtClaimsConstant;
import com.atey.dto.UserLoginDto;
import com.atey.entity.User;
import com.atey.enumeration.UserStatusEnum;
import com.atey.enumeration.UserTypeEnum;
import com.atey.exception.BaseException;
import com.atey.mapper.ManageLoginMapper;
import com.atey.message.LoginMessage;
import com.atey.properties.JwtProperties;
import com.atey.service.ManageLoginService;
import com.atey.utils.JwtUtil;
import com.atey.vo.ManageLoginVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ManageLoginImpl extends ServiceImpl<ManageLoginMapper, User> implements ManageLoginService {

    private final JwtProperties jwtProperties;

    @Override
    public ManageLoginVo login(UserLoginDto dto) {

        String username = dto.getUsername();
        String password = dto.getPassword();

        //判断用户名是否重复
        User user = lambdaQuery()
                .eq(StrUtil.isNotBlank(username), User::getUsername, username)
                .eq(User::getType, UserTypeEnum.MANAGE.getValue())
                .one();

        if(ObjectUtil.isNull(user)) {
            throw new BaseException(LoginMessage.USERNAME_PASSWORD_ERROR);
        }

        if(!password.equals(user.getPassword())) {
            throw new BaseException(LoginMessage.USERNAME_PASSWORD_ERROR);
        }

        //判断账号是否冻结
        if(ObjectUtil.equals(user.getStatus(), UserStatusEnum.FAIL.getKey())) {
            throw new BaseException(LoginMessage.USER_FAIL);
        }

        //用户名和密码正确，生成token
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.MANAGE_ID,user.getId());
        claims.put(JwtClaimsConstant.TYPE,user.getType());

        String token = JwtUtil.generateJwt(jwtProperties.getManageSecretKey(), jwtProperties.getManageTtl(), claims);

        ManageLoginVo userLoginVo = new ManageLoginVo();

        userLoginVo.setUserId(user.getId());
        userLoginVo.setToken(token);

        return userLoginVo;
    }
}
