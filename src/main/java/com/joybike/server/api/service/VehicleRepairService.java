package com.joybike.server.api.service;

import com.joybike.server.api.model.vehicleRepair;

/**
 * Created by lishaoyong on 16/10/23.
 */
public interface VehicleRepairService {


    /**
     * 故障上报
     *
     * @param vehicleRepair
     * @return
     */
    long addVehicleRepair(vehicleRepair vehicleRepair) throws Exception;
}
