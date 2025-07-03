package com.atey.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoleAuthorities {
    //角色名称
    private String role;
    //权限集合
    private List<String> authorities;
}
