package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ErrorEnum;
import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.vehicleHeartbeat;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.util.RestfulException;
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
    public int updateOrderCode(long id, String orderCode)  throws Exception{

        try {
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            map.put("id", id);
            return execSQL(updateOrderCodeSql, map);
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取没有支付的用户
     *
     * @param vehicleId
     * @return
     */
    final String getNoPayByUserIdSql = "select * from vehicleOrder where userId = : userId and status = : status";

    @Override
    public vehicleOrder getNoPayByUserId(long userId)  throws Exception{
        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("status", OrderStatus.complete.getValue());

            try {
                return (vehicleOrder) this.jdbcTemplate.queryForObject(getNoPayByUserIdSql, map, new BeanPropertyRowMapper(vehicleOrder.class));
            } catch (Exception e) {
                return null;
            }

        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }

    }

}
