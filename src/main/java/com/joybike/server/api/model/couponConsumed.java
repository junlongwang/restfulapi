package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class couponConsumed  implements Serializable {
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
     * 代金券ID
     *  @Author lisy
    **/
    private Long couponId;

    /** 
     * 消费时间
     *  @Author lisy
    **/
    private Integer executeAt;

    /** 
     * 订单code
     *  @Author lisy
    **/
    private String orderCode;

    /** 
     * 优惠金额
     *  @Author lisy
    **/
    private BigDecimal awardAmount;

    /** 
     * 备注
     *  @Author lisy
    **/
    private String remark;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public couponConsumed(Long userId, Long couponId, Integer executeAt, String orderCode, BigDecimal awardAmount, String remark, Integer createAt) {
        this.userId = userId;
        this.couponId = couponId;
        this.executeAt = executeAt;
        this.orderCode = orderCode;
        this.awardAmount = awardAmount;
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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getExecuteAt() {
        return executeAt;
    }

    public void setExecuteAt(Integer executeAt) {
        this.executeAt = executeAt;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public BigDecimal getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(BigDecimal awardAmount) {
        this.awardAmount = awardAmount;
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