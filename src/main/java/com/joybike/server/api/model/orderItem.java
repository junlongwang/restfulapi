package com.joybike.server.api.model;

import java.io.Serializable;

public class orderItem implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 用户id
     *  @Author lisy
    **/
    private Long userId;

    /** 
     * 订单编码
     *  @Author lisy
    **/
    private String orderCode;

    /** 
     * 车辆编码
     *  @Author lisy
    **/
    private String vehicleCode;

    /** 
     * 骑行开始时间
     *  @Author lisy
    **/
    private Integer beginAt;

    /** 
     * 骑行结束时间
     *  @Author lisy
    **/
    private Integer endAt;

    /** 
     * 骑行开始的纬度
     *  @Author lisy
    **/
    private String beginDimension;

    /** 
     * 骑行开始的经度
     *  @Author lisy
    **/
    private String beginLongitude;

    /** 
     * 骑行结束的纬度
     *  @Author lisy
    **/
    private String endDimension;

    /** 
     * 骑行结束的经度
     *  @Author lisy
    **/
    private String endLongitude;

    /** 
     * 骑行轨迹图
     *  @Author lisy
    **/
    private String cyclingImg;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public orderItem(Long userId, String orderCode, String vehicleCode, Integer beginAt, Integer endAt, String beginDimension, String beginLongitude, String endDimension, String endLongitude, String cyclingImg, Integer createAt) {
        this.userId = userId;
        this.orderCode = orderCode;
        this.vehicleCode = vehicleCode;
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.beginDimension = beginDimension;
        this.beginLongitude = beginLongitude;
        this.endDimension = endDimension;
        this.endLongitude = endLongitude;
        this.cyclingImg = cyclingImg;
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

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode == null ? null : vehicleCode.trim();
    }

    public Integer getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Integer beginAt) {
        this.beginAt = beginAt;
    }

    public Integer getEndAt() {
        return endAt;
    }

    public void setEndAt(Integer endAt) {
        this.endAt = endAt;
    }

    public String getBeginDimension() {
        return beginDimension;
    }

    public void setBeginDimension(String beginDimension) {
        this.beginDimension = beginDimension == null ? null : beginDimension.trim();
    }

    public String getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(String beginLongitude) {
        this.beginLongitude = beginLongitude == null ? null : beginLongitude.trim();
    }

    public String getEndDimension() {
        return endDimension;
    }

    public void setEndDimension(String endDimension) {
        this.endDimension = endDimension == null ? null : endDimension.trim();
    }

    public String getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        this.endLongitude = endLongitude == null ? null : endLongitude.trim();
    }

    public String getCyclingImg() {
        return cyclingImg;
    }

    public void setCyclingImg(String cyclingImg) {
        this.cyclingImg = cyclingImg == null ? null : cyclingImg.trim();
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}