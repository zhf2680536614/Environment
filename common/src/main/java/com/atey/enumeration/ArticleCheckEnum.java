package com.atey.enumeration;

import lombok.Getter;

@Getter
public enum ArticleCheckEnum {

    PASS(1,"审核通过"),
    ING(2,"审核中"),
    NO_PASS(3,"审核未通过");

    private final Integer key;

    private final String value;

    ArticleCheckEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
