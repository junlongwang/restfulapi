package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.orderItem;
import com.joybike.server.api.model.vehicleOrder;

import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/21.
 */
public interface OrderItemDao extends IRepository<orderItem> {

    /**
     * 获取订单信息
     */
    orderItem getOrderItemByUser(long userId, String bicycleCode) throws Exception;


    /**
     * 修改资源信息
     *
     * @param userId
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @param beforePrice
     * @return
     * @throws Exception
     */
    int updateOrderByLock(long userId, String bicycleCode, int endAt, double endLongitude, double endDimension, int cyclingTime) throws Exception;
}
