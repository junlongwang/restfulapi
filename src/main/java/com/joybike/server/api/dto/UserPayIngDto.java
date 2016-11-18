package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lishaoyong on 16/11/13.
 */
public class UserPayIngDto  implements Serializable {

    /**
     * restType：是否支付完成。0 钱不够 1:支付完成
     */
    @JSONField(ordinal = 1)
    private Integer restType;

    /**
     * 支付完成的信息
     */
    @JSONField(ordinal = 2)
    private VehicleOrderDto vehicleOrderDto;


    public UserPayIngDto() {

    }

    public UserPayIngDto(Integer restType, VehicleOrderDto vehicleOrderDto) {
        this.restType = restType;
        this.vehicleOrderDto = vehicleOrderDto;
    }

    @Override
    public String toString() {
        return "UserPayIngDto{" +
                "restType=" + restType +
                ", vehicleOrderDto=" + vehicleOrderDto +
                '}';
    }

    public Integer getRestType() {
        return restType;
    }

    public void setRestType(Integer restType) {
        this.restType = restType;
    }

    public VehicleOrderDto getVehicleOrderDto() {
        return vehicleOrderDto;
    }

    public void setVehicleOrderDto(VehicleOrderDto vehicleOrderDto) {
        this.vehicleOrderDto = vehicleOrderDto;
    }
}
