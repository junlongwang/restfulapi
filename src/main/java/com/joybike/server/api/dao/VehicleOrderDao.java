package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.vehicleOrder;

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
    int updateOrderCode(long id, String orderCode);

    /**
     * 根据车辆ID获取该车使用的使用
     *
     * @param vehicleId
     * @return
     */
    vehicleOrder getOrderByVehicleId(String vehicleId);

    /**
     * 根据用户ID获取未完成订单
     */
    vehicleOrder getOrderByUserId(long userId);
}
