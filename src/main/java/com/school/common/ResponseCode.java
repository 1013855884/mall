package com.school.common;

public enum ResponseCode {
    SUCCESS(0,"成功"),
    ERROR(1,"错误"),
    NEED_LOGIN(10,"请登录"),
    ILLEGAL_ARGUMENT(2,"非法参数");
    private final Integer status;
    private final String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    ResponseCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
