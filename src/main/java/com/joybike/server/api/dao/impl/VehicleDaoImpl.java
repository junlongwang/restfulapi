package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.model.vehicle;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Repository("VehicleDao")
public class VehicleDaoImpl extends Reository<vehicle> implements VehicleDao {

    /**
     * 修改车辆状态
     *
     * @param vehicleId
     * @return
     */
    final String vehicleStatusSql = "update vehicle set useStatus = :useStatus , updateAt = :updateAt where vehicleId = :vehicleId";

    @Override
    public int updateVehicleStatus(String vehicleId, UseStatus useStatus) {
        Map map = new HashMap();
        map.put("vehicleId", vehicleId);
        map.put("useStatus", useStatus.getValue());
        map.put("updateAt", UnixTimeUtils.now());
        return execSQL(vehicleStatusSql, map);
    }


}
