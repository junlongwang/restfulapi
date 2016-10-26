package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.vehicleOrder;

import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface VehicleOrderDao extends IRepository<vehicleOrder> {


    /**
     * 修改订单code
     *
     * @param id
     * @return
     */
    int updateOrderCode(long id, String orderCode) throws Exception;


    /**
     * 根据用户ID获取未完成订单
     */
    vehicleOrder getNoPayByUserId(long userId) throws Exception;

    /**
     * 锁车时修改订单
     *
     * @param userId
     * @param bicycleCode
     * @return
     * @throws Exception
     */
    int updateOrderByLock(long userId, String bicycleCode, BigDecimal beforePrice) throws Exception;


    /**
     * 修改订单支付状态
     *
     * @param orderCode
     * @return
     */
    int updateStatausByCode(String  orderCode);


}
