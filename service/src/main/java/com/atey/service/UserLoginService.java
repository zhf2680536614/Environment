package com.atey.service;

import com.atey.dto.UserLoginDto;
import com.atey.entity.User;
import com.atey.vo.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserLoginService extends IService<User> {
    UserLoginVo login(UserLoginDto dto);
}
