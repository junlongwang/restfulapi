package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/20.
 */
public class subscribeInfo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 车辆印刷的id
     */
    private String vehicleId;

    /**
     * 预约开始时间
     */
    private Integer startAt;

    /**
     * 预约结束时间
     */
    private Integer endAt;

    /**
     * 创建时间
     */
    private Integer createAt;

    /**
     * 预约状态 1：预约中 ，2使用中
     */
    private Integer status;

    /**
     * orderCode
     */
    private String subscribeCode;

    /**
     * 纬度
     */
    private BigDecimal dimension;

    /**
     * 经度
     */
    private BigDecimal longitude;


    public subscribeInfo(Long id, Long userId, String vehicleId, Integer startAt, Integer endAt, Integer createAt, Integer status, String subscribeCode, BigDecimal dimension, BigDecimal longitude) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createAt = createAt;
        this.status = status;
        this.subscribeCode = subscribeCode;
        this.dimension = dimension;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "subscribeInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", vehicleId='" + vehicleId + '\'' +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", createAt=" + createAt +
                ", status=" + status +
                ", subscribeCode='" + subscribeCode + '\'' +
                ", dimension=" + dimension +
                ", longitude=" + longitude +
                '}';
    }

    public subscribeInfo(){

    }

    public BigDecimal getDimension() {
        return dimension;
    }

    public void setDimension(BigDecimal dimension) {
        this.dimension = dimension;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public void setSubscribeCode(String subscribeCode) {
        this.subscribeCode = subscribeCode;
    }

    public String getSubscribeCode() {
        return subscribeCode;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getStartAt() {
        return startAt;
    }

    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    public Integer getEndAt() {
        return endAt;
    }

    public void setEndAt(Integer endAt) {
        this.endAt = endAt;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}
