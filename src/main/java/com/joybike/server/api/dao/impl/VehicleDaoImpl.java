package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.model.vehicle;
import org.springframework.stereotype.Service;

/**
 * Created by 58 on 2016/10/22.
 */
@Service
public class VehicleDaoImpl extends Reository<vehicle> implements VehicleDao {
}
