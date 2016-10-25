package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.*;
import com.joybike.server.api.model.*;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 58 on 2016/10/22.
 */
@Repository
public class VehicleHeartbeatDaoImpl extends Reository<vehicleHeartbeat> implements VehicleHeartbeatDao {

    /**
     * 获取骑行记录
     *
     * @param bicycleCode
     * @param beginAt
     * @param endAt
     * @return
     */
    final String getVehicleHeartbeatListSql = "select * from vehicleHeartbeat where lockId = ? and createAt between ? and ?";
    @Override
    public List<vehicleHeartbeat> getVehicleHeartbeatList(long lockId, int beginAt, int endAt)  throws Exception{
        try {
            Object[] objects = new Object[]{lockId , beginAt ,endAt};
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getVehicleHeartbeatListSql, objects, new BeanPropertyRowMapper(vehicleHeartbeat.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }

    }
}
