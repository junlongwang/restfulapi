package com.joybike.server.api.service.impl;

import com.joybike.server.api.dao.VehicleRepairDao;
import com.joybike.server.api.model.vehicleRepair;
import com.joybike.server.api.service.VehicleRepairService;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class VehicleRepairServiceImpl implements VehicleRepairService {

    @Autowired
    VehicleRepairDao vehicleRepairDao;

    /**
     * 故障上报
     *
     * @param vehicleRepair
     * @return
     */
    @Override
    public long addVehicleRepair(vehicleRepair vehicleRepair) throws Exception {
        vehicleRepair.setCreateAt(UnixTimeUtils.now());
        vehicleRepair.setDisposeStatus(0);
        return vehicleRepairDao.save(vehicleRepair);
    }
}
