package com.atey.enumeration;

import lombok.Getter;

@Getter
public enum ArticleStatusEnum {

    DRAFT(1,"草稿"),
    POST(2,"已发布");

    private final Integer key;

    private final String value;

    ArticleStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
