package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private BigDecimal beginDimension;

    /** 
     * 骑行开始的经度
     *  @Author lisy
    **/
    private BigDecimal beginLongitude;

    /** 
     * 骑行结束的纬度
     *  @Author lisy
    **/
    private BigDecimal endDimension;

    /** 
     * 骑行结束的经度
     *  @Author lisy
    **/
    private BigDecimal endLongitude;

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

    /**
     * 骑行时间- 秒
     */
    private Integer cyclingTime;

    /**
     * 骑行距离
     */
    private BigDecimal tripDist;

    /**
     * 开始地理位置
     */
    private String startAddress;

    /**
     * 结束地理位置
     */
    private String endAddress;


    public orderItem(){

    }

    @Override
    public String toString() {
        return "orderItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderCode='" + orderCode + '\'' +
                ", vehicleCode='" + vehicleCode + '\'' +
                ", beginAt=" + beginAt +
                ", endAt=" + endAt +
                ", beginDimension=" + beginDimension +
                ", beginLongitude=" + beginLongitude +
                ", endDimension=" + endDimension +
                ", endLongitude=" + endLongitude +
                ", cyclingImg='" + cyclingImg + '\'' +
                ", createAt=" + createAt +
                ", cyclingTime=" + cyclingTime +
                ", tripDist=" + tripDist +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                '}';
    }

    public orderItem(Long id, Long userId, String orderCode, String vehicleCode, Integer beginAt, Integer endAt, BigDecimal beginDimension, BigDecimal beginLongitude, BigDecimal endDimension, BigDecimal endLongitude, String cyclingImg, Integer createAt, Integer cyclingTime, BigDecimal tripDist, String startAddress, String endAddress) {
        this.id = id;
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
        this.cyclingTime = cyclingTime;
        this.tripDist = tripDist;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public BigDecimal getTripDist() {
        return tripDist;
    }

    public void setTripDist(BigDecimal tripDist) {
        this.tripDist = tripDist;
    }

    public void setCyclingTime(Integer cyclingTime) {
        this.cyclingTime = cyclingTime;
    }

    public Integer getCyclingTime() {
        return cyclingTime;
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

    public void setBeginDimension(BigDecimal beginDimension) {
        this.beginDimension = beginDimension;
    }

    public BigDecimal getBeginDimension() {
        return beginDimension;
    }

    public void setBeginLongitude(BigDecimal beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public BigDecimal getBeginLongitude() {
        return beginLongitude;
    }

    public void setEndDimension(BigDecimal endDimension) {
        this.endDimension = endDimension;
    }

    public BigDecimal getEndDimension() {
        return endDimension;
    }

    public void setEndLongitude(BigDecimal endLongitude) {
        this.endLongitude = endLongitude;
    }

    public BigDecimal getEndLongitude() {
        return endLongitude;
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