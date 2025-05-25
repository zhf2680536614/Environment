package com.atey.enumeration;

import lombok.Getter;

@Getter
public enum ArticleHotEnum {
    HOT(1,"热门"),
    NOT_HOT(0,"非热");

    private final Integer key;

    private final String value;

    ArticleHotEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
