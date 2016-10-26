package com.joybike.server.api.service.impl;


import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.dao.OrderItemDao;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.orderItem;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.util.StringRandom;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class OrderRestfulServiceImpl implements OrderRestfulService {

    @Autowired
    VehicleOrderDao vehicleOrderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    BicycleRestfulService bicycleRestfulService;


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
    @Transactional
    @Override
    public long addOrder(long userId, String vehicleId, int beginAt, double beginLongitude ,double beginDimension) throws Exception {
        subscribeInfo vinfo = bicycleRestfulService.getSubscribeInfoByBicycleCode(vehicleId);
        subscribeInfo uInfo = bicycleRestfulService.getSubscribeInfoByUserId(userId);

        if (vinfo != null && uInfo != null) {
            bicycleRestfulService.updateSubscribeInfo(userId, vehicleId);
        }

        if (uInfo != null && vinfo == null) {
            bicycleRestfulService.updateSubscribeInfo(userId, vehicleId);
            bicycleRestfulService.deleteSubscribeInfo(userId, uInfo.getVehicleId());
            bicycleRestfulService.vehicleSubscribe(userId, vehicleId, beginAt);
            bicycleRestfulService.updateSubscribeInfo(userId, vehicleId);
        }


        //创建订单
        vehicleOrder order = new vehicleOrder();
        order.setOrderCode(StringRandom.getStringRandom(14));
        order.setUserId(userId);
        order.setBeforePrice(BigDecimal.valueOf(0));
        order.setAfterPrice(BigDecimal.valueOf(0));
        order.setPayId(Long.valueOf(0));
        order.setStatus(OrderStatus.newly.getValue());
        order.setVehicleId(vehicleId);
        order.setCreateAt(UnixTimeUtils.now());
        order.setExucuteAt(beginAt);
        long orderId = vehicleOrderDao.save(order);
        String orderCode = StringRandom.GenerateOrderCode((int) orderId, userId);

        vehicleOrderDao.updateOrderCode(orderId, orderCode);


        //创建订单订单资源
        orderItem item = new orderItem();
        item.setUserId(userId);
        item.setOrderCode(orderCode);
        item.setVehicleCode(vehicleId);
        item.setBeginAt(beginAt);
        item.setBeginDimension(BigDecimal.valueOf(beginDimension));
        item.setBeginLongitude(BigDecimal.valueOf(beginLongitude));
        orderItemDao.save(item);
        return orderId;
    }

    /**
     * 根据用户ID获取用户未支付订单
     *
     * @param userId
     * @return
     */
    @Override
    public vehicleOrder getNoPayOrderByUserId(long userId) throws Exception {
        return vehicleOrderDao.getNoPayByUserId(userId);
    }

    /**
     * 根据车辆ID获取使用该车的订单信息
     *
     * @param vehicleId
     * @return
     */
    @Override
    public vehicleOrder getOrderByVehicleId(String vehicleId) throws Exception {
//        subscribeInfo subscribeInfo = subscribeInfoService.getSubscribeInfoByVehicleId(vehicleId, SubscribeStatus.use);
//        vehicleOrder vehicleOrder = vehicleOrderDao.getOrderByVehicleId(vehicleId);
//
//        if (vehicleOrder != null && subscribeInfo != null) {
//            //判断未完成的订单与预约中执行的订单是不是同一个,双向保证
//            if (subscribeInfo.getUserId().equals(vehicleOrder.getUserId())) {
//                return vehicleOrder;
//            } else {
//                return null;
//            }
//        } else {
//            return null;
//        }
//
//    }
        return null;
    }
}
