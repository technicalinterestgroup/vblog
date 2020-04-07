package com.technicalinterest.group.service.Enum;

public enum TypeEnum {
    BLOG((short)1, "博客"),
    COMMENT((short)2, "评论"),
    NOTICE((short)3, "通告");

    private Short code;
    private String message;

    TypeEnum(Short code, String message) {
        this.code = code;
        this.message = message;
    }

    public Short getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
