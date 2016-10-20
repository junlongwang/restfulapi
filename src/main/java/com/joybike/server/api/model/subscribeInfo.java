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
    private Long vehicleId;

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

    public subscribeInfo(Long id, Long userId, Long vehicleId, Integer startAt, Integer endAt, Integer createAt) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createAt = createAt;
    }

    public subscribeInfo(){

    }

    @Override
    public String toString() {
        return "subscribeInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", vehicleId=" + vehicleId +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
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
