package com.joybike.server.api.dao.impl;

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
    public int updateVehicleStatus(String vehicleId, UseStatus useStatus) {
        Map map = new HashMap();
        map.put("vehicleId", vehicleId);
        map.put("useStatus", useStatus.getValue());
        map.put("updateAt", UnixTimeUtils.now());
        return execSQL(vehicleStatusSql, map);
    }

    /**
     * 根据车辆code获取车辆使用状态
     *
     * @param bicycleCode
     * @return
     */
    final String getVehicleUseStatusByBicycleCodeSql = "select useStatus from vehicle where status = 0 and vehicleId = :vehicleId";

    @Override
    public int getVehicleUseStatusByBicycleCode(String bicycleCode) {
        Map map = new HashMap();
        map.put("vehicleId", bicycleCode);
        try {
            return (Integer) this.jdbcTemplate.queryForObject(getVehicleUseStatusByBicycleCodeSql, map, new BeanPropertyRowMapper(Integer.class));
        } catch (Exception e) {
            return -1;
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
    public int getVehicleStatusByBicycleCode(String bicycleCode) {
        Map map = new HashMap();
        map.put("vehicleId", bicycleCode);
        try {
            return (Integer) this.jdbcTemplate.queryForObject(getVehicleStatusByBicycleCodeSql, map, new BeanPropertyRowMapper(Integer.class));
        } catch (Exception e) {
            return -1;
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
    public List<vehicle> getVehicleList(double beginDimension, double beginLongitude) {
        Double maxLat = beginDimension + UnixGps.doLatDegress(beginDimension);
        Double minLat = beginDimension - UnixGps.doLatDegress(beginDimension);
        Double maxLng = beginLongitude + UnixGps.doLngDegress(beginLongitude);
        Double minLng = beginLongitude + UnixGps.doLngDegress(beginLongitude);

        System.out.println(UnixGps.doLatDegress(beginDimension) + "：1");
        System.out.println(UnixGps.doLngDegress(beginLongitude) +  "：2");
        Object[] object = new Object[]{maxLat, minLat, maxLng, minLng};
        List<vehicle> list = this.jdbcTemplate.getJdbcOperations().query(getVehicleListSql, object, new BeanPropertyRowMapper(vehicle.class));
        return list;
    }
}
