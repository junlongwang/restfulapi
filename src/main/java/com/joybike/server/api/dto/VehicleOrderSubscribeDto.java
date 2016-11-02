package com.joybike.server.api.dto;



import com.joybike.server.api.model.subscribeInfo;

import java.io.Serializable;


/**
 * Created by lishaoyong on 16/10/29.
 * 用户骑行订单的返回
 */
public class VehicleOrderSubscribeDto implements Serializable {

    private VehicleOrderDto vehicleOrderDto;

    private subscribeInfo info;

    public VehicleOrderSubscribeDto(VehicleOrderDto vehicleOrderDto, subscribeInfo info) {
        this.vehicleOrderDto = vehicleOrderDto;
        this.info = info;
    }

    public VehicleOrderSubscribeDto() {

    }


    public VehicleOrderDto getVehicleOrderDto() {
        return vehicleOrderDto;
    }

    public void setVehicleOrderDto(VehicleOrderDto vehicleOrderDto) {
        this.vehicleOrderDto = vehicleOrderDto;
    }

    public subscribeInfo getInfo() {
        return info;
    }

    public void setInfo(subscribeInfo info) {
        this.info = info;
    }
}
