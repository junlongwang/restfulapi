package com.joybike.server.api.model;

import java.math.BigDecimal;

/**
 * 消费明细
 * Created by 58 on 2016/10/16.
 */
public class ConsumeLog {
    private String title;
    private int consumeAt;
    private int createAt;
    private int endAt;
    private int minutes;
    private BigDecimal amount;
    private BigDecimal awardAmount;
    private BigDecimal couponAmount;
    private String orderCode;

    public ConsumeLog(int consumeAt, int createAt, int endAt, int minutes, BigDecimal amount, BigDecimal awardAmount, BigDecimal couponAmount, String orderCode) {
        this.title = "车辆骑行";
        this.consumeAt = consumeAt;
        this.createAt = createAt;
        this.endAt = endAt;
        this.minutes = minutes;
        this.amount = amount;
        this.awardAmount = awardAmount;
        this.couponAmount = couponAmount;
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getConsumeAt() {
        return consumeAt;
    }

    public void setConsumeAt(int consumeAt) {
        this.consumeAt = consumeAt;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(BigDecimal awardAmount) {
        this.awardAmount = awardAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }
}
