package com.lzc.crowd.entity.vo;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class MemberConfirmInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *  易付宝账号
     */
    private String payNum;
    /**
     * 法人身份证号
     */
    private String cardNum;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Override
    public String toString() {
        return "MemberConfirmInfoVO{" +
                "payNum='" + payNum + '\'' +
                ", cardNum='" + cardNum + '\'' +
                '}';
    }
}

