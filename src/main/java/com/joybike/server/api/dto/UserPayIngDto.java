package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lishaoyong on 16/11/13.
 */
public class UserPayIngDto  implements Serializable {

    /**
     * 是否支付完成:0 否 1:余额不足
     */
    @JSONField(ordinal = 1)
    private Integer payType;

    /**
     * 支付完成的信息
     */
    @JSONField(ordinal = 2)
    private VehicleOrderDto vehicleOrderDto;


    public UserPayIngDto() {

    }

    public UserPayIngDto(Integer payType, VehicleOrderDto vehicleOrderDto) {
        this.payType = payType;
        this.vehicleOrderDto = vehicleOrderDto;
    }

    @Override
    public String toString() {
        return "UserPayIngDto{" +
                "payType=" + payType +
                ", vehicleOrderDto=" + vehicleOrderDto +
                '}';
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public VehicleOrderDto getVehicleOrderDto() {
        return vehicleOrderDto;
    }

    public void setVehicleOrderDto(VehicleOrderDto vehicleOrderDto) {
        this.vehicleOrderDto = vehicleOrderDto;
    }
}
