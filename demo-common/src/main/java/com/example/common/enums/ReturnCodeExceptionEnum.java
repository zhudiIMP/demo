package com.example.common.enums;

public enum ReturnCodeExceptionEnum {
    SUCCESS("00000000", "成功"),
    RET_SUCCESS("00000", "成功"),
    PARAM_INVALID_ERROR("60000001", "参数非法"),
    SYSTEM_IS_BUSY_ERROR("60000002", "系统繁忙,请稍后重试！"),
    REDIS_ERROR("60000003", "Redis异常"),
    COPY_PROPERTIES_ERROR("60000004","对象属性拷贝失败！") ,
    QUERY_OUTTIME("60000005","处理超时！") ,
    DATABASE_QUERY_ERROR("60000007","数据库操作失败！") ,
    UNKNOWN_ERROR("99999999", "系统未知异常");
    //queryScope
    private String errorCode;
    private String errorMessage;

    ReturnCodeExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
