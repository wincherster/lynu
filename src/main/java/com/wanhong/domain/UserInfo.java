package com.wanhong.domain;

import com.wanhong.domain.common.Page;

import java.util.Date;

/**
 * @author wangmeng247
 * @date 2018-02-09 11:39
 */
public class UserInfo{
    private Long userId;
    private String userName;
    private String nickName;
    private String password;
    private String phone;
    private String msgExpr;
    private String msgCode;
    private Date createDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsgExpr() {
        return msgExpr;
    }

    public void setMsgExpr(String msgExpr) {
        this.msgExpr = msgExpr;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
