package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.vehicle;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface VehicleDao extends IRepository<vehicle> {


    /**
     * 修改车状态信息
     *
     * @param vehicleId 车身印刷ID
     * @param useStatus 车辆使用状态
     * @return
     */
    int updateVehicleStatus(String vehicleId, UseStatus useStatus);


    /**
     * 根据车辆编码获取车辆状态
     *
     * @param bicycleCode
     * @return
     */
    int getVehicleUseStatusByBicycleCode(String bicycleCode);


    /**
     * 根据车ID获取车的状态
     *
     * @param bicycleCode
     * @return
     */
    int getVehicleStatusByBicycleCode(String bicycleCode);


    /**
     * 获取当前一公里内的所有车辆
     *
     * @param beginDimension
     * @param beginLongitude
     * @return
     */
    List<vehicle> getVehicleList(double beginDimension, double beginLongitude);

}
