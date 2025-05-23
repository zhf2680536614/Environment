package com.atey.service;

import com.atey.dto.UserLoginDto;
import com.atey.entity.User;
import com.atey.vo.ManageLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ManageLoginService extends IService<User> {
    ManageLoginVo login(UserLoginDto dto);
}
