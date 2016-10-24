package com.joybike.server.api.model;

import java.io.Serializable;

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
     * 修改时间
     */
    private Integer updateAt;

    /**
     * 预约单Code，生成规则为用户ID+车辆编号
     */
    private String subscribeCode;

    /**
     * 订单code，骑行订单的code
     */
    private String orderCode;

    public subscribeInfo(Long id, Long userId, String vehicleId, Integer startAt, Integer endAt, Integer createAt, Integer status, Integer updateAt,String subscribeCode ,String orderCode) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createAt = createAt;
        this.status = status;
        this.updateAt = updateAt;
        this.subscribeCode = subscribeCode;
        this.orderCode = orderCode;
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
                ", updateAt=" + updateAt +
                ",subscribeCode=" + subscribeCode +
                ",orderCode=" + orderCode +
                '}';
    }

    public subscribeInfo(){

    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setSubscribeCode(String subscribeCode) {
        this.subscribeCode = subscribeCode;
    }

    public String getSubscribeCode() {
        return subscribeCode;
    }

    public void setUpdateAt(Integer updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getUpdateAt() {
        return updateAt;
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
