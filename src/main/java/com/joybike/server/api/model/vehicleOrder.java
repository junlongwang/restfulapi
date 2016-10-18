package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class vehicleOrder implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 订单code
     *  @Author lisy
    **/
    private String orderCode;

    /** 
     * 用户ID
     *  @Author lisy
    **/
    private String userId;

    /** 
     * 折前金额
     *  @Author lisy
    **/
    private BigDecimal beforePrice;

    /** 
     * 折扣金额
     *  @Author lisy
    **/
    private BigDecimal afterPrice;

    /** 
     * 支付ID
     *  @Author lisy
    **/
    private Long payId;

    /** 
     * 状态
     *  @Author lisy
    **/
    private Integer status;

    /** 
     * 备注
     *  @Author lisy
    **/
    private String remark;

    /** 
     * 扩展信息
     *  @Author lisy
    **/
    private String extension;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    /** 
     * 执行时间
     *  @Author lisy
    **/
    private Integer exucuteAt;

    public vehicleOrder(String orderCode, String userId, BigDecimal beforePrice, BigDecimal afterPrice, Long payId, Integer status, String remark, String extension, Integer createAt, Integer exucuteAt) {
        this.orderCode = orderCode;
        this.userId = userId;
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
        this.payId = payId;
        this.status = status;
        this.remark = remark;
        this.extension = extension;
        this.createAt = createAt;
        this.exucuteAt = exucuteAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getBeforePrice() {
        return beforePrice;
    }

    public void setBeforePrice(BigDecimal beforePrice) {
        this.beforePrice = beforePrice;
    }

    public BigDecimal getAfterPrice() {
        return afterPrice;
    }

    public void setAfterPrice(BigDecimal afterPrice) {
        this.afterPrice = afterPrice;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public Integer getExucuteAt() {
        return exucuteAt;
    }

    public void setExucuteAt(Integer exucuteAt) {
        this.exucuteAt = exucuteAt;
    }
}