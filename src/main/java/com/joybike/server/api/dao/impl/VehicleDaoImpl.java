package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.VehicleEnableType;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixGps;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.model.vehicle;
import com.joybike.server.api.util.UnixTimeUtils;

import java.util.HashMap;
import java.util.List;
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
    public int updateVehicleUseStatus(String vehicleId, UseStatus useStatus) throws Exception {
        try {
            Map map = new HashMap();
            map.put("vehicleId", vehicleId);
            map.put("useStatus", useStatus.getValue());
            map.put("updateAt", UnixTimeUtils.now());
            return execSQL(vehicleStatusSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 根据车辆code获取车辆使用状态
     *
     * @param bicycleCode
     * @return
     */
    final String getVehicleUseStatusByBicycleCodeSql = "select useStatus from vehicle where status = 0 and vehicleId = :vehicleId";

    @Override
    public int getVehicleUseStatusByBicycleCode(String bicycleCode) throws Exception {
        try {
            Map map = new HashMap();
            map.put("vehicleId", bicycleCode);
            try {
                return this.jdbcTemplate.queryForObject(getVehicleUseStatusByBicycleCodeSql, map, Integer.class);
            } catch (Exception e) {
                return -1;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取车辆状态本身
     *
     * @param bicycleCode
     * @return
     */
    final String getVehicleStatusByBicycleCodeSql = "select status from vehicle where vehicleId = :vehicleId";

    @Override
    public int getVehicleStatusByBicycleCode(String bicycleCode) throws Exception {
        try {
            Map map = new HashMap();
            map.put("vehicleId", bicycleCode);
            try {
                return this.jdbcTemplate.queryForObject(getVehicleStatusByBicycleCodeSql, map, Integer.class);
            } catch (Exception e) {
                return -1;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取当前位置一公里范围的车辆
     *
     * @param beginDimension
     * @param beginLongitude
     * @return
     */
    final String getVehicleListSql = "select * from vehicle where status = 0 and useStatus = 0 and (lastDimension between ? and ?) " +
            "and (lastLongitude between ? and ?)";

    @Override
    public List<vehicle> getVehicleList(double beginDimension, double beginLongitude) throws Exception {
        try {
            //最大小维度
            Double maxLat = beginDimension + UnixGps.doDimension(beginDimension);
            Double minLat = beginDimension - UnixGps.doDimension(beginDimension);
            //最大小经度,经度是负数,+是小，-是大
            Double maxLng = beginLongitude - UnixGps.doLongitude(beginLongitude);
            Double minLng = beginLongitude + UnixGps.doLongitude(beginLongitude);

            Object[] object = new Object[]{minLat, maxLat, minLng, maxLng};
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getVehicleListSql, object, new BeanPropertyRowMapper(vehicle.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 更具车身编号获取车锁Id
     *
     * @param bicycleCode
     * @return
     */
    final String getLockByBicycleCodeSql = "select lockId from vehicle where vehicleId = :vehicleId";

    @Override
    public long getLockByBicycleCode(String bicycleCode) throws Exception {
        try {
            Map map = new HashMap();
            map.put("vehicleId", bicycleCode);

            try {
                return this.jdbcTemplate.queryForObject(getLockByBicycleCodeSql, map, Integer.class);
            } catch (Exception e) {
                return -1;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }


    /**
     * 修改车的状态
     *
     * @param vehicleId         车身印刷ID
     * @param vehicleEnableType 车辆状态
     * @return
     * @throws Exception
     */
    final String updateVehicleStatusSql = "update vehicle set status = :status , updateAt = :updateAt where vehicleId = :vehicleId";

    @Override
    public int updateVehicleStatus(String vehicleId, VehicleEnableType vehicleEnableType) throws Exception {
        try {
            Map map = new HashMap();
            map.put("vehicleId", vehicleId);
            map.put("status", vehicleEnableType);
            map.put("updateAt", UnixTimeUtils.now());
            return execSQL(updateVehicleStatusSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
