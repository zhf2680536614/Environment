package com.atey.enumeration;

import lombok.Getter;

@Getter
public enum UserStatusEnum {

    NORMAL("0","正常"),
    FAIL("1","冻结");

    private final String key;
    private final String value;
    UserStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
