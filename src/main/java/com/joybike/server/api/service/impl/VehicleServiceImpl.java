package com.joybike.server.api.service.impl;

import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.model.vehicle;
import com.joybike.server.api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleDao vehicleDao;

    /**
     * 获取车辆使用状态
     *
     * @param bicycleCode
     * @return
     */
    @Override
    public int getVehicleUseStatusByBicycleCode(String bicycleCode) throws Exception {
        return vehicleDao.getVehicleUseStatusByBicycleCode(bicycleCode);
    }

    /**
     * 获取车辆本身的状态
     *
     * @param bicycleCode
     * @return
     * @throws Exception
     */
    @Override
    public int getVehicleStatusByBicycleCode(String bicycleCode) throws Exception {
        return vehicleDao.getVehicleStatusByBicycleCode(bicycleCode);
    }

    @Override
    public List<vehicle> getVehicleList(double beginDimension, double beginLongitude) {
        return vehicleDao.getVehicleList(beginDimension,beginLongitude);
    }
}
