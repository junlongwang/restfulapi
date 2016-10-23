package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.dao.OrderItemDao;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.orderItem;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.service.SubscribeInfoService;
import com.joybike.server.api.service.VehicleOrderService;
import com.joybike.server.api.util.StringRandom;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Service
public class VehicleOrderServiceImpl implements VehicleOrderService {

    @Autowired
    VehicleOrderDao vehicleOrderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    SubscribeInfoService subscribeInfoService;


    /**
     * 扫码
     *
     * @param userId         用户ID
     * @param vehicleId      车身印刷ID
     * @param beginAt        开始时间
     * @param beginDimension 骑行开始的经度
     * @param beginLongitude 骑行开始的维度
     * @return
     */
    @Override
    public void addOrder(long userId, String vehicleId, int beginAt, BigDecimal beginDimension, BigDecimal beginLongitude) {

        //修改预约信息,修改预约状态时判断是不是预约的这辆车,如果是,修改状态，如果不是重新插入一条信息

        //根据车辆查找
        subscribeInfo subscribeinfoVehicle = subscribeInfoService.getSubscribeInfoByVehicleId(vehicleId, SubscribeStatus.subscribe);

        if (subscribeinfoVehicle != null) {

            if (userId == subscribeinfoVehicle.getUserId() && vehicleId.equals(subscribeinfoVehicle.getVehicleId())) {
                subscribeInfoService.updateSubscribeInfo(userId, vehicleId);
            }
        }

        //根据人查找
        subscribeInfo subscribeinfoUser = subscribeInfoService.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);

        if (subscribeinfoUser != null) {

            if (userId == subscribeinfoUser.getUserId() && !vehicleId.equals(subscribeinfoUser.getVehicleId())) {
                subscribeInfoService.deleteSubscribeInfo(userId, subscribeinfoUser.getVehicleId());
                try {
                    subscribeInfoService.VehicleSubscribe(userId, vehicleId, UnixTimeUtils.now());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                subscribeInfoService.updateSubscribeInfo(userId, vehicleId);
            }
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
        item.setBeginDimension(beginDimension);
        item.setBeginLongitude(beginLongitude);
        orderItemDao.save(item);

    }

    /**
     * @param userId
     * @return
     */
    @Override
    public vehicleOrder getOrderInfoByUserId(long userId) {
        return vehicleOrderDao.getOrderByUserId(userId);
    }

    /**
     * 根据车辆ID获取使用该车的订单信息
     *
     * @param vehicleId
     * @return
     */
    @Override
    public vehicleOrder getOrderByVehicleId(String vehicleId) {
        subscribeInfo subscribeInfo = subscribeInfoService.getSubscribeInfoByVehicleId(vehicleId, SubscribeStatus.use);
        vehicleOrder vehicleOrder = vehicleOrderDao.getOrderByVehicleId(vehicleId);

        if (vehicleOrder != null && subscribeInfo != null) {
            //判断未完成的订单与预约中执行的订单是不是同一个,双向保证
            if (subscribeInfo.getUserId().equals(vehicleOrder.getUserId())) {
                return vehicleOrder;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }
}
