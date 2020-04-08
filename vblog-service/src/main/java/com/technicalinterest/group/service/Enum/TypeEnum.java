package com.technicalinterest.group.service.Enum;

public enum TypeEnum {
    BLOG((short)1, "博客"),
    COMMENT((short)2, "评论"),
    NOTICE((short)3, "通告"),
    ASK((short)4, "问答");

    private Short code;
    private String message;

    TypeEnum(Short code, String message) {
        this.code = code;
        this.message = message;
    }

    public short getCode() {
        return code.shortValue();
    }

    public String getMessage() {
        return message;
    }
}
