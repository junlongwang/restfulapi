package com.joybike.server.api.model;

import java.io.Serializable;

public class userCoupon implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 代金券Id
     *  @Author lisy
    **/
    private Long couponId;

    /** 
     * 用户ID
     *  @Author lisy
    **/
    private Long userId;

    /** 
     * 过期时间
     *  @Author lisy
    **/
    private Integer expireAt;

    /** 
     * 状态:0:正常，1：已使用，2：已过期
     *  @Author lisy
    **/
    private Integer status;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public userCoupon(Long couponId, Long userId, Integer expireAt, Integer status, Integer createAt) {
        this.couponId = couponId;
        this.userId = userId;
        this.expireAt = expireAt;
        this.status = status;
        this.createAt = createAt;
    }

    public userCoupon(){

    }

    @Override
    public String toString() {
        return "userCoupon{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", expireAt=" + expireAt +
                ", status=" + status +
                ", createAt=" + createAt +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Integer expireAt) {
        this.expireAt = expireAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}