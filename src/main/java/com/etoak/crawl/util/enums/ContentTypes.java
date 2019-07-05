package com.etoak.crawl.util.enums;


public enum ContentTypes {

    JSON("application/json"), FORM("application/x-www-form-urlencoded"),STRING("application/json; charset=UTF-8");
    private String value;

    ContentTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
