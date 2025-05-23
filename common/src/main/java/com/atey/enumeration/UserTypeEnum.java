package com.atey.enumeration;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    USER("1","user"),
    MANAGE("0","manage");

    private final String key;

    private final String value;

    UserTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
