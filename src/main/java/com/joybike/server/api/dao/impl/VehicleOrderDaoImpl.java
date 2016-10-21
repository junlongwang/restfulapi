package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.vehicleOrder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Repository("VehicleOrderDao")
public class VehicleOrderDaoImpl extends Reository<vehicleOrder> implements VehicleOrderDao {


    /**
     * 修改订单code
     *
     * @param id
     * @return
     */
    final String updateOrderCodeSql = "update vehicleOrder set orderCode = :orderCode where id = :id";

    @Override
    public int updateOrderCode(long id, String orderCode) {
        Map map = new HashMap();
        map.put("orderCode", orderCode);
        map.put("id", id);
        return execSQL(updateOrderCodeSql, map);
    }

    /**
     * 根据车辆ID获取该车使用的用户
     *
     * @param vehicleId
     * @return
     */
    final String getOrderByVehicleIdSql = "select * from vehicleOrder where vehicleId = :vehicleId and status = :status";

    @Override
    public vehicleOrder getOrderByVehicleId(String vehicleId) {
        Map map = new HashMap();
        map.put("vehicleId", vehicleId);
        map.put("status", OrderStatus.newly.getValue());
        try {
            return (vehicleOrder) this.jdbcTemplate.queryForObject(getOrderByVehicleIdSql, map, new BeanPropertyRowMapper(vehicleOrder.class));
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 根据车辆ID获取该车使用的用户
     *
     * @param vehicleId
     * @return
     */
    final String getOrderByUserIdSql = "select * from vehicleOrder where userId = : userId and status = : status";

    @Override
    public vehicleOrder getOrderByUserId(long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("status", OrderStatus.newly.getValue());
        return (vehicleOrder) this.jdbcTemplate.queryForObject(getOrderByUserIdSql, map, new BeanPropertyRowMapper(vehicleOrder.class));
    }

}
