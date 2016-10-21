package com.joybike.server.api.service;

import com.joybike.server.api.model.orderItem;

/**
 * Created by lishaoyong on 16/10/21.
 */
public interface OrderItemService {


    /**
     * 根据车辆code与用户Id获取订单
     *
     * @param vehicleCode
     * @return
     */
    orderItem getItemByVehicleCode(String vehicleCode);


}
