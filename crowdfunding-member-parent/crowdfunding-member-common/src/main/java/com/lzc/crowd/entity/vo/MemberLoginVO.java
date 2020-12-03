package com.lzc.crowd.entity.vo;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class MemberLoginVO implements Serializable {

    private Integer id;

    private String loginAcct;

    private String email;

    private String phoneNum;



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

    @Override
    public String toString() {
        return "MemberLoginVO{" +
                "id=" + id +
                ", loginAcct='" + loginAcct + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
