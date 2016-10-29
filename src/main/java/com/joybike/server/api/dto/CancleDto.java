package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by lishaoyong on 16/10/29.
 */
public class CancleDto {

    /**
     * 用户ID
     */
    @JSONField(ordinal = 1)
    private Long userId;


    /**
     * 车辆code
     */
    @JSONField(ordinal = 2)
    private String bicycleCode;


    public CancleDto(){}

    public CancleDto(Long userId, String bicycleCode) {
        this.userId = userId;
        this.bicycleCode = bicycleCode;
    }

    @Override
    public String toString() {
        return "SubscribeDto{" +
                "userId=" + userId +
                ", bicycleCode='" + bicycleCode + '\'' +
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

}
