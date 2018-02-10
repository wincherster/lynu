package com.wanhong.domain;

import com.wanhong.common.errorcode.BusinessCode;

/**
 * @author wangmeng247
 * @date 2018-02-08 16:57
 */
public class ResultJson<T> {
    private String code;
    private String msg;
    private T data;
    ResultJson(){
    }

    public ResultJson(BusinessCode businessCode) {
        this.code = businessCode.getStringCode();
        this.msg = businessCode.getMsg();
    }

    public ResultJson(BusinessCode businessCode,T data) {
        this.code = businessCode.getStringCode();
        this.msg = businessCode.getMsg();
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
