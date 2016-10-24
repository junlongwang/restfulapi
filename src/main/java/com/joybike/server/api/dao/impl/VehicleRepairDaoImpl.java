package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleRepairDao;
import com.joybike.server.api.model.vehicleRepair;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Repository("VehicleRepairDao")
public class VehicleRepairDaoImpl extends Reository<vehicleRepair> implements VehicleRepairDao {
}
