package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.OrderItemDao;
import com.joybike.server.api.model.orderItem;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/21.
 */
@Repository("OrderItemDao")
public class OrderItemDaoImpl extends Reository<orderItem> implements OrderItemDao {


    /**
     * @param userId
     * @param bicycleCode
     * @return
     * @throws Exception
     */
    final String getOrderItemByUserSql = "select * from orderItem where where orderCode = :orderCode";

    @Override
    public orderItem getOrderItemByOrderCode(String orderCode) throws Exception {
        try {
            Map map = new HashMap();
            map.put("orderCode", orderCode);
            try {
                return (orderItem) this.jdbcTemplate.queryForObject(getOrderItemByUserSql, map, new BeanPropertyRowMapper(orderItem.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }


    /**
     * @param userId
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @param beforePrice
     * @return
     * @throws Exception
     */
    final String updateOrderItemByLockSql = "update orderItem set endAt = :endAt,cyclingTime = :cyclingTime ,endDimension = :endDimension,endLongitude = :endLongitude where userId = :userId and vehicleId = :vehicleId";

    @Override
    public int updateOrderByLock(long userId, String bicycleCode, int endAt, double endLongitude, double endDimension, int cyclingTime) throws Exception {
        Map itemMap = new HashMap();
        itemMap.put("endAt", endAt);
        itemMap.put("endDimension", endDimension);
        itemMap.put("endLongitude", endLongitude);
        itemMap.put("userId", userId);
        itemMap.put("vehicleId", bicycleCode);
        itemMap.put("cyclingTime",cyclingTime);
        return execSQL(updateOrderItemByLockSql, itemMap);
    }
}
