package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.Enum.VehicleEnableType;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.vehicle;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface VehicleDao extends IRepository<vehicle> {


    /**
     * 修改车使用状态信息
     *
     * @param vehicleId 车身印刷ID
     * @param useStatus 车辆使用状态
     * @return
     */
    int updateVehicleUseStatus(String vehicleId, UseStatus useStatus) throws Exception;


    /**
     * 根据车辆编码获取车辆状态
     *
     * @param bicycleCode
     * @return
     */
    vehicle getVehicleStatusByBicycleCode(String bicycleCode) throws Exception;


    /**
     * 获取当前一公里内的所有车辆
     *
     * @param beginDimension
     * @param beginLongitude
     * @return
     */
    List<vehicle> getVehicleList(double beginDimension, double beginLongitude) throws Exception;


    /**
     * 根据BicycleCode获取车锁
     * @param bicycleCode
     * @return
     */
    long getLockByBicycleCode(String bicycleCode) throws Exception;

    /**
     * 修改车的状态信息
     *
     * @param vehicleId 车身印刷ID
     * @param vehicleEnableType 车辆状态
     * @return
     */
    int updateVehicleStatus(String vehicleId, VehicleEnableType vehicleEnableType) throws Exception;
}
