package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.vehicleOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Repository("VehicleOrderDao")
public class VehicleOrderDaoImpl extends Reository<vehicleOrder> implements VehicleOrderDao {
}
