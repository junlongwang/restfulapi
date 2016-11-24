package com.joybike.server.api.dto;

import java.io.Serializable;

/**
 * Created by 58 on 2016/11/24.
 */
public class H5PostMessageDto implements Serializable {

    private String openId; //: 必传
    private String  payStatus; //: 0 --> 支付成功、1-->支付失败 (必传)
    private String  mobile; //: 用户手机号 (必传)
    private String  orderSeq;/// : 订单号 支付成功则传，失败不需要传
    private String  money; //: 支付成功的金额数，失败不需要传 (例如：2)
    private String  balance; //: 用户余额，支付成功不需要传 如 (-2)
    private String userId;

    public H5PostMessageDto(String openId, String payStatus, String mobile, String orderSeq, String money, String balance, String userId) {
        this.openId = openId;
        this.payStatus = payStatus;
        this.mobile = mobile;
        this.orderSeq = orderSeq;
        this.money = money;
        this.balance = balance;
        this.userId = userId;
    }

    public H5PostMessageDto() {
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
