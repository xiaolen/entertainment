package com.etoak.crawl.util.enums;


public enum Encodings {
    UTF8("UTF-8"), GBK("GBK"), ISO88591("ISO-8859-1"),  GB2312("GB2312"),;
    private String value;

    Encodings(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
