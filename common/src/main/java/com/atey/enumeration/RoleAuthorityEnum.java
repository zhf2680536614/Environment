package com.atey.enumeration;

import lombok.Getter;

@Getter
public enum RoleAuthorityEnum {

    NORMAL("0","启用"),
    FAIL("1","禁用");

    private final String key;
    private final String value;
    RoleAuthorityEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
