package com.atey.mapper;

import com.atey.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManageLoginMapper extends BaseMapper<User> {
}
