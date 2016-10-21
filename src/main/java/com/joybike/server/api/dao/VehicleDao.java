package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.vehicle;

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

    
}
