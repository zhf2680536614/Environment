package com.atey.mapper;

import com.atey.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户-角色关系表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2025-07-03
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
