package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
    public int updateOrderCode(long id, String orderCode) throws Exception {

        try {
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            map.put("id", id);
            return execSQL(updateOrderCodeSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取没有支付的用户
     *
     * @param vehicleId
     * @return
     */
    final String getNoPayByUserIdSql = "select * from vehicleOrder where userId = :userId and status = :status";

    @Override
    public vehicleOrder getNoPayByUserId(long userId) throws Exception {
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("status", OrderStatus.end.getValue());

            try {
                return (vehicleOrder) this.jdbcTemplate.queryForObject(getNoPayByUserIdSql, map, new BeanPropertyRowMapper(vehicleOrder.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }

    }

    /**
     * 锁车时修改订单
     *
     * @param userId
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @return
     * @throws Exception
     */
    final String updateOrderByLockSql = "update vehicleOrder set beforePrice = :beforePrice,afterPrice = :afterPrice,status = :status where userId = :userId and vehicleId = :vehicleId";

    @Override
    public int updateOrderByLock(long userId, String bicycleCode, BigDecimal beforePrice) throws Exception {
        Map orderMap = new HashMap();
        orderMap.put("beforePrice", beforePrice);
        orderMap.put("status", OrderStatus.end.getValue());
        orderMap.put("userId", userId);
        orderMap.put("afterPrice", beforePrice);
        orderMap.put("vehicleId", bicycleCode);
        return execSQL(updateOrderByLockSql, orderMap);
    }

    /**
     * 修改订单支付状态
     *
     * @param id
     * @return
     */
    final String updateByIdSql = "update vehicleOrder set status = :status where orderCode = :orderCode";

    @Override
    public int updateStatausByCode(String orderCode) {
        Map orderMap = new HashMap();
        orderMap.put("orderCode", orderCode);
        orderMap.put("status", OrderStatus.complete.getValue());
        return execSQL(updateOrderByLockSql, orderMap);
    }

}
