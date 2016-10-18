package com.joybike.server.api.po;

import java.math.BigDecimal;

public class bankConsumedOrder {
    /** 
     * 
     * @Author lisy
    **/
    private Long id;

    /** 
     * 用户ID
     * @Author lisy
    **/
    private Long userId;

    /** 
     * 订单code
     * @Author lisy
    **/
    private String orderCode;

    /** 
     * 消费金额
     * @Author lisy
    **/
    private BigDecimal payAmount;

    /** 
     * 充值金额
     * @Author lisy
    **/
    private BigDecimal depositAmount;

    /** 
     * 交易时间
     * @Author lisy
    **/
    private Integer payAt;

    /** 
     * 状态:0/完成,1/退款
     * @Author lisy
    **/
    private Integer status;

    /** 
     * 退款时间
     * @Author lisy
    **/
    private Integer refundAt;

    /** 
     * 备注
     * @Author lisy
    **/
    private String remark;

    /** 
     * 创建时间
     * @Author lisy
    **/
    private Integer createAt;

    public bankConsumedOrder(Long userId, String orderCode, BigDecimal payAmount, BigDecimal depositAmount, Integer payAt, Integer status, Integer refundAt, String remark, Integer createAt) {
        this.userId = userId;
        this.orderCode = orderCode;
        this.payAmount = payAmount;
        this.depositAmount = depositAmount;
        this.payAt = payAt;
        this.status = status;
        this.refundAt = refundAt;
        this.remark = remark;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getPayAt() {
        return payAt;
    }

    public void setPayAt(Integer payAt) {
        this.payAt = payAt;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}