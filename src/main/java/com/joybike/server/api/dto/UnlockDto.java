package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by lishaoyong on 16/10/29.
 */
public class UnlockDto {

    /**
     * 用户ID
     */
    @JSONField(ordinal = 1)
    private Long userId;

    /**
     * 车辆Code
     */
    @JSONField(ordinal = 2)
    private String bicycleCode;

    /**
     * 骑行开始时间
     */
    @JSONField(ordinal = 3)
    private int beginAt;

    /**
     * 开始经度
     */
    @JSONField(ordinal = 4)
    private Double beginLongitude;


    /**
     * 开始纬度
     */
    @JSONField(ordinal = 5)
    private Double beginDimension;

    private UnlockDto(){

    }

    public UnlockDto(Long userId, String bicycleCode, int beginAt, Double beginLongitude, Double beginDimension) {
        this.userId = userId;
        this.bicycleCode = bicycleCode;
        this.beginAt = beginAt;
        this.beginLongitude = beginLongitude;
        this.beginDimension = beginDimension;
    }

    @Override
    public String toString() {
        return "UnlockDto{" +
                "userId=" + userId +
                ", bicycleCode='" + bicycleCode + '\'' +
                ", beginAt=" + beginAt +
                ", beginLongitude=" + beginLongitude +
                ", beginDimension=" + beginDimension +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBicycleCode() {
        return bicycleCode;
    }

    public void setBicycleCode(String bicycleCode) {
        this.bicycleCode = bicycleCode;
    }

    public int getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(int beginAt) {
        this.beginAt = beginAt;
    }

    public Double getBeginLongitude() {
        return beginLongitude;
    }

    public void setBeginLongitude(Double beginLongitude) {
        this.beginLongitude = beginLongitude;
    }

    public Double getBeginDimension() {
        return beginDimension;
    }

    public void setBeginDimension(Double beginDimension) {
        this.beginDimension = beginDimension;
    }
}
