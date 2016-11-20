package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/11/15.
 */
public class UserPayOrderDto implements Serializable {

//    String bicycleCode, int endAt, double endLongitude, double endDimension

    /**
     * 车辆ID
     *  @Author lisy
     **/
    @JSONField(ordinal = 1)
    private String bicycleCode;

    /**
     * 结束时间
     *  @Author lisy
     **/
    @JSONField(ordinal = 2)
    private int endAt;

    /**
     * 纬度
     *  @Author lisy
     **/
    @JSONField(ordinal = 3)
    private double endLongitude;

    /**

     * 经度
     *  @Author lisy
     **/
    @JSONField(ordinal = 4)
    private double endDimension;

    public UserPayOrderDto(){
    }

    public UserPayOrderDto(String bicycleCode, int endAt, double endLongitude, double endDimension) {
        this.bicycleCode = bicycleCode;
        this.endAt = endAt;
        this.endLongitude = endLongitude;
        this.endDimension = endDimension;
    }

    @Override
    public String toString() {
        return "UserPayOrderDto{" +
                "bicycleCode='" + bicycleCode + '\'' +
                ", endAt=" + endAt +
                ", endLongitude=" + endLongitude +
                ", endDimension=" + endDimension +
                '}';
    }

    public String getBicycleCode() {
        return bicycleCode;
    }

    public void setBicycleCode(String bicycleCode) {
        this.bicycleCode = bicycleCode;
    }

    public int getEndAt() {
        return endAt;
    }

    public void setEndAt(int endAt) {
        this.endAt = endAt;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public double getEndDimension() {
        return endDimension;
    }

    public void setEndDimension(double endDimension) {
        this.endDimension = endDimension;
    }
}
