package com.joybike.server.api.service;

import com.joybike.server.api.model.vehicle;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/23.
 */
public interface VehicleService {


    /**
     * 获取车辆使用状态
     *
     * @param bicycleCode
     * @return
     */
    int getVehicleUseStatusByBicycleCode(String bicycleCode) throws Exception;

    /**
     * 获取车辆使用状态
     *
     * @param bicycleCode
     * @return
     */
    int getVehicleStatusByBicycleCode(String bicycleCode) throws Exception;

    /**
     * 获取当前位置一公里内的车辆
     *
     * @param beginDimension
     * @param beginLongitude
     * @return
     */
    List<vehicle> getVehicleList(double beginDimension, double beginLongitude) throws Exception;
}
