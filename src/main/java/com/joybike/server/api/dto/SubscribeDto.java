package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by lishaoyong on 16/10/29.
 */
public class SubscribeDto implements Serializable {

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


    /**
     * 开始时间
     */
    @JSONField(ordinal = 3)
    private int beginAt;

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

    public SubscribeDto(){}

    public SubscribeDto(Long userId, String bicycleCode, int beginAt) {
        this.userId = userId;
        this.bicycleCode = bicycleCode;
        this.beginAt = beginAt;
    }

    @Override
    public String toString() {
        return "SubscribeDto{" +
                "userId=" + userId +
                ", bicycleCode='" + bicycleCode + '\'' +
                ", beginAt=" + beginAt +
                '}';
    }
}
