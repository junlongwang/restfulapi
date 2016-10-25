package com.joybike.server.api.service;


import com.joybike.server.api.model.vehicleOrder;

import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/23.
 */
public interface OrderRestfulService {

    /**
     * 创建订单
     *
     * @param userId         用户ID
     * @param vehicleId      车身印刷ID
     * @param beginAt        开始时间
     * @param beginLongitude 骑行开始的经度
     * @param beginDimension 骑行开始的维度
     * @return
     */
    void addOrder(long userId, String vehicleId, int beginAt, BigDecimal beginLongitude , BigDecimal beginDimension) throws Exception;


    /**
     * 根据用户ID获取用户未完成的订单
     *
     * @param userId
     * @return
     */
    vehicleOrder getNoPayOrderByUserId(long userId) throws Exception;

    /**
     * 根据车辆ID获取用户未完成订单
     *
     * @param vehicleId
     * @return
     */
    vehicleOrder getOrderByVehicleId(String vehicleId) throws Exception;


}
