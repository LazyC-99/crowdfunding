package com.lzc.crowd.entity.po;

import java.io.Serializable;

/**
 * t_member
 * @author
 */
public class MemberPO implements Serializable {
    private Integer id;

    private String loginAcct;

    private String userPwd;

    private String email;

    private String phoneNum;

    /**
     * 实名认证状态  0-未实名 1-申请中  2-已认证
     */
    private Integer authStatus;

    /**
     * 0-个人  1-企业
     */
    private Integer userType;

    private String realName;

    private String cardNum;

    /**
     * 0-个人   1-个体  2-企业  3-政府
     */
    private Integer acctType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getAcctType() {
        return acctType;
    }

    public void setAcctType(Integer acctType) {
        this.acctType = acctType;
    }

    @Override
    public String toString() {
        return "MemberPO{" +
                "id=" + id +
                ", loginAcct='" + loginAcct + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", authStatus=" + authStatus +
                ", userType=" + userType +
                ", realName='" + realName + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", acctType=" + acctType +
                '}';
    }
}