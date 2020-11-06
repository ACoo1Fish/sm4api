package com.sm4.system.common;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(4004, "参数检验失败"),
    UNAUTHORIZED(4001, "暂未登录或token已经过期"),
    FORBIDDEN(4003, "没有相关权限"),
    CREDENTIALSERROR(4006,"密码错误"),
    CREDENTIALSEXPIRED(4007,"密码过期"),
    ACCOUNTDISABLE(4008, "账号不可用"),
    ACCOUNTLOCKED(4009, "账号被锁定"),
    ACCOUNTNOTEXIST(4010, "账号不存在"),
    ACCOUNTOTHERS(4011, "账号下线"),
    ACCOUNTEXPIRED(4005,"账号过期");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
