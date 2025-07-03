package com.atey.mapper;

import com.atey.entity.RAConfig;
import com.atey.entity.RoleAuthorities;
import com.atey.entity.RoleAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色-权限关系表 Mapper 接口
 * </p>
 *
 * @author atey
 * @since 2025-07-03
 */
@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {

    List<RAConfig> query(String auth);
}
