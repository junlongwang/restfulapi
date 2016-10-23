package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.*;

import java.util.List;

/**
 * Created by 58 on 2016/10/22.
 */
public interface VehicleHeartbeatDao extends IRepository<vehicleHeartbeat> {


    /**
     *
     * @param lockId
     * @param beginAt
     * @param endAt
     * @return
     */
    List<vehicleHeartbeat> getVehicleHeartbeatList(long lockId, int beginAt, int endAt);
}
