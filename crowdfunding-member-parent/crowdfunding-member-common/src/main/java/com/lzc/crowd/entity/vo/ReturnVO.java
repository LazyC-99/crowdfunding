package com.lzc.crowd.entity.vo;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class ReturnVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 回报类型：0 - 实物回报， 1 虚拟物品回报
     */
    private Integer type;
    /**
     * 支持金额
     */
    private Integer supportMoney;
    /**
     * 回报内容介绍
     */
    private String content;
    /**
     * 总回报数量，0 为不限制
     */
    private Integer count;
    /**
     * 是否限制单笔购买数量，0 表示不限购，1 表示限购
     */
    private Integer signalPurchase;
    /**
     * 如果单笔限购，那么具体的限购数量
     */
    private Integer purchase;
    /**
     * 运费，“0”为包邮
     */
    private Integer freight;
    /**
     * 是否开发票，0 - 不开发票， 1 - 开发票
     */
    private Integer invoice;
    /**
     * 众筹结束后返还回报物品天数
     */
    private Integer returnDate;
    /**
     * 说明图片路径
     */
    private String picPath;

    @Override
    public String toString() {
        return "ReturnVO{" +
                "type=" + type +
                ", supportMoney=" + supportMoney +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", signalPurchase=" + signalPurchase +
                ", purchase=" + purchase +
                ", freight=" + freight +
                ", invoice=" + invoice +
                ", returnDate=" + returnDate +
                ", picPath='" + picPath + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Integer supportMoney) {
        this.supportMoney = supportMoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSignalPurchase() {
        return signalPurchase;
    }

    public void setSignalPurchase(Integer signalPurchase) {
        this.signalPurchase = signalPurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Integer getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Integer returnDate) {
        this.returnDate = returnDate;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
