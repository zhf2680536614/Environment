package com.atey.service.impl;

import com.atey.entity.Authority;
import com.atey.mapper.AuthorityMapper;
import com.atey.service.IAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author atey
 * @since 2025-07-03
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

}
