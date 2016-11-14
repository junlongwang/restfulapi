package com.joybike.server.api.dto;

import com.joybike.server.api.model.vehicleHeartbeat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishaoyong on 16/10/29.
 * 用户骑行订单的返回
 */
public class VehicleOrderDto implements Serializable {

    /**
     * @Author lisy
     **/
    private Long id;

    /**
     * 订单code
     *
     * @Author lisy
     **/
    private String orderCode;

    /**
     * 用户ID
     *
     * @Author lisy
     **/
    private Long userId;

    /**
     * 折前金额
     *
     * @Author lisy
     **/
    private BigDecimal beforePrice;

    /**
     * 折后金额
     *
     * @Author lisy
     **/
    private BigDecimal afterPrice;

    /**
     * 支付ID
     *
     * @Author lisy
     **/
    private Long payId;

    /**
     * 状态1:新建，2:骑行结束，15：支付完成
     *
     * @Author lisy
     **/
    private Integer status;

    /**
     * 车辆code
     */
    private String vehicleId;


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
     * 骑行时间- 秒
     */
    private Integer cyclingTime;

    /**
     * 骑行距离
     */
    private BigDecimal tripDist;

    /**
     * 坐标数据
     */
    private List<vehicleHeartbeat> vehicleHeartbeatList;


    /**
     * 轨迹图
     */
    private String cyclingImg;

    public VehicleOrderDto() {

    }

    @Override
    public String toString() {
        return "VehicleOrderDto{" +
                "id=" + id +
                ", orderCode='" + orderCode + '\'' +
                ", userId=" + userId +
                ", beforePrice=" + beforePrice +
                ", afterPrice=" + afterPrice +
                ", payId=" + payId +
                ", status=" + status +
                ", vehicleId='" + vehicleId + '\'' +
                ", beginAt=" + beginAt +
                ", endAt=" + endAt +
                ", beginDimension=" + beginDimension +
                ", beginLongitude=" + beginLongitude +
                ", endDimension=" + endDimension +
                ", endLongitude=" + endLongitude +
                ", cyclingTime=" + cyclingTime +
                ", tripDist=" + tripDist +
                ", vehicleHeartbeatList=" + vehicleHeartbeatList +
                ", cyclingImg='" + cyclingImg + '\'' +
                '}';
    }

    public VehicleOrderDto(Long id, String orderCode, Long userId, BigDecimal beforePrice, BigDecimal afterPrice, Long payId, Integer status, String vehicleId, Integer beginAt, Integer endAt, BigDecimal beginDimension, BigDecimal beginLongitude, BigDecimal endDimension, BigDecimal endLongitude, Integer cyclingTime, BigDecimal tripDist, List<vehicleHeartbeat> vehicleHeartbeatList, String cyclingImg) {
        this.id = id;
        this.orderCode = orderCode;
        this.userId = userId;
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
        this.payId = payId;
        this.status = status;
        this.vehicleId = vehicleId;
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.beginDimension = beginDimension;
        this.beginLongitude = beginLongitude;
        this.endDimension = endDimension;
        this.endLongitude = endLongitude;
        this.cyclingTime = cyclingTime;
        this.tripDist = tripDist;
        this.vehicleHeartbeatList = vehicleHeartbeatList;
        this.cyclingImg = cyclingImg;
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
        this.orderCode = orderCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
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

    public BigDecimal getBeginDimension() {
        return beginDimension;
    }

    public void setBeginDimension(BigDecimal beginDimension) {
        this.beginDimension = beginDimension;
    }

    public BigDecimal getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(BigDecimal beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public BigDecimal getEndDimension() {
        return endDimension;
    }

    public void setEndDimension(BigDecimal endDimension) {
        this.endDimension = endDimension;
    }

    public BigDecimal getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(BigDecimal endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Integer getCyclingTime() {
        return cyclingTime;
    }

    public void setCyclingTime(Integer cyclingTime) {
        this.cyclingTime = cyclingTime;
    }

    public List<vehicleHeartbeat> getVehicleHeartbeatList() {
        return vehicleHeartbeatList;
    }

    public void setVehicleHeartbeatList(List<vehicleHeartbeat> vehicleHeartbeatList) {
        this.vehicleHeartbeatList = vehicleHeartbeatList;
    }

    public void setCyclingImg(String cyclingImg) {
        this.cyclingImg = cyclingImg;
    }

    public String getCyclingImg() {
        return cyclingImg;
    }

    public void setTripDist(BigDecimal tripDist) {
        this.tripDist = tripDist;
    }

    public BigDecimal getTripDist() {
        return tripDist;
    }
}
