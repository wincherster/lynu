package com.wanhong.common.errorcode;

/**
 * @author wangmeng247
 * @date 2018-02-08 17:02
 */
public enum BusinessCode {
    SUCCESS("0000", "请求成功."),
    NOT_LOG_IN("0001", "未登录，请先登录"),
    IS_LOG_IN("0002", "已登录"),
    ILLEGAL_ARG_ERROR("0003", "请求参数错误."),
    UNKNOWN_ERROR("0004", "未知异常."),
    ;
    private final String code;
    private final String msg;

    BusinessCode(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getStringCode() {
        return this.code;
    }
    public String getMsg() {
        return this.msg;
    }

}
