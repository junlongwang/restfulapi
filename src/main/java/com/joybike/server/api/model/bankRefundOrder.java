package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class bankRefundOrder implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 用户ID
     *  @Author lisy
    **/
    private Long userId;

    /** 
     * 退款目标账户
     *  @Author lisy
    **/
    private String colleAcount;

    /** 
     * 退款金额
     *  @Author lisy
    **/
    private BigDecimal refundAmount;

    /** 
     * 备注
     *  @Author lisy
    **/
    private String remark;

    /** 
     * 通知时间
     *  @Author lisy
    **/
    private Integer notifyAt;

    /** 
     * 通知状态1:未通知;2,退款成功;3,退款失败
     *  @Author lisy
    **/
    private Integer notifyStatus;

    /** 
     * 通知日志
     *  @Author lisy
    **/
    private String notifyLog;

    /** 
     * 回调URL
     *  @Author lisy
    **/
    private String callbackUrl;

    /** 
     * 回调状态1,未回调; 2,回调失败; 3,回调成功
     *  @Author lisy
    **/
    private Integer callbackStatus;

    /** 
     * 回调次数
     *  @Author lisy
    **/
    private Integer callbackNumbers;

    /** 
     * 最后回调时间
     *  @Author lisy
    **/
    private Integer lastcallAt;

    /** 
     * 退款类型:0:押金退款，1：骑行订单退款
     *  @Author lisy
    **/
    private Integer refundType;

    /** 
     * 如果是押金退款对应的是充值ID，如果是骑行退款对应的订单id
     *  @Author lisy
    **/
    private Long orderId;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public bankRefundOrder(Long userId, String colleAcount, BigDecimal refundAmount, String remark, Integer notifyAt, Integer notifyStatus, String notifyLog, String callbackUrl, Integer callbackStatus, Integer callbackNumbers, Integer lastcallAt, Integer refundType, Long orderId, Integer createAt) {
        this.userId = userId;
        this.colleAcount = colleAcount;
        this.refundAmount = refundAmount;
        this.remark = remark;
        this.notifyAt = notifyAt;
        this.notifyStatus = notifyStatus;
        this.notifyLog = notifyLog;
        this.callbackUrl = callbackUrl;
        this.callbackStatus = callbackStatus;
        this.callbackNumbers = callbackNumbers;
        this.lastcallAt = lastcallAt;
        this.refundType = refundType;
        this.orderId = orderId;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getColleAcount() {
        return colleAcount;
    }

    public void setColleAcount(String colleAcount) {
        this.colleAcount = colleAcount == null ? null : colleAcount.trim();
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getNotifyAt() {
        return notifyAt;
    }

    public void setNotifyAt(Integer notifyAt) {
        this.notifyAt = notifyAt;
    }

    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getNotifyLog() {
        return notifyLog;
    }

    public void setNotifyLog(String notifyLog) {
        this.notifyLog = notifyLog == null ? null : notifyLog.trim();
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
    }

    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    public Integer getCallbackNumbers() {
        return callbackNumbers;
    }

    public void setCallbackNumbers(Integer callbackNumbers) {
        this.callbackNumbers = callbackNumbers;
    }

    public Integer getLastcallAt() {
        return lastcallAt;
    }

    public void setLastcallAt(Integer lastcallAt) {
        this.lastcallAt = lastcallAt;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}