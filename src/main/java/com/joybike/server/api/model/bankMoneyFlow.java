package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class bankMoneyFlow implements Serializable {
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
     * 交易类型1：充值，2：消费，3：退消费(内退)4：充值退款(外退)
     *  @Author lisy
    **/
    private Integer dealType;

    /** 
     * 来源类型1：支付宝，2：微信
     *  @Author lisy
    **/
    private String sourceType;

    /** 
     * 充值ID
     *  @Author lisy
    **/
    private Long depositId;

    /** 
     * orderCode
     *  @Author lisy
    **/
    private String sourceOrderCode;

    /** 
     * 交易时间
     *  @Author lisy
    **/
    private Integer payAt;

    /** 
     * 消费ID
     *  @Author lisy
    **/
    private Long consumedId;

    /** 
     * 现金
     *  @Author lisy
    **/
    private BigDecimal cash;

    /** 
     * 赠送金额
     *  @Author lisy
    **/
    private BigDecimal award;

    /** 
     * 消费状态(1：初始，3：成功，4:未扣费, 5:已经退款成功)
     *  @Author lisy
    **/
    private Integer status;

    /** 
     * 退款时间
     *  @Author lisy
    **/
    private Integer refundAt;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public bankMoneyFlow(){

    }

    public bankMoneyFlow(Long userId, Integer dealType, String sourceType, Long depositId, String sourceOrderCode, Integer payAt, Long consumedId, BigDecimal cash, BigDecimal award, Integer status, Integer refundAt, Integer createAt) {
        this.userId = userId;
        this.dealType = dealType;
        this.sourceType = sourceType;
        this.depositId = depositId;
        this.sourceOrderCode = sourceOrderCode;
        this.payAt = payAt;
        this.consumedId = consumedId;
        this.cash = cash;
        this.award = award;
        this.status = status;
        this.refundAt = refundAt;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "bankMoneyFlow{" +
                "id=" + id +
                ", userId=" + userId +
                ", dealType=" + dealType +
                ", sourceType='" + sourceType + '\'' +
                ", depositId=" + depositId +
                ", sourceOrderCode='" + sourceOrderCode + '\'' +
                ", payAt=" + payAt +
                ", consumedId=" + consumedId +
                ", cash=" + cash +
                ", award=" + award +
                ", status=" + status +
                ", refundAt=" + refundAt +
                ", createAt=" + createAt +
                '}';
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

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public String getSourceOrderCode() {
        return sourceOrderCode;
    }

    public void setSourceOrderCode(String sourceOrderCode) {
        this.sourceOrderCode = sourceOrderCode == null ? null : sourceOrderCode.trim();
    }

    public Integer getPayAt() {
        return payAt;
    }

    public void setPayAt(Integer payAt) {
        this.payAt = payAt;
    }

    public Long getConsumedId() {
        return consumedId;
    }

    public void setConsumedId(Long consumedId) {
        this.consumedId = consumedId;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getAward() {
        return award;
    }

    public void setAward(BigDecimal award) {
        this.award = award;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRefundAt() {
        return refundAt;
    }

    public void setRefundAt(Integer refundAt) {
        this.refundAt = refundAt;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}