package com.joybike.server.api.service.impl;


import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.dao.OrderItemDao;
import com.joybike.server.api.dao.ProductDao;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.StringRandom;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    ProductDao productDao;

    @Autowired
    BicycleRestfulService bicycleRestfulService;

    @Autowired
    PayRestfulService payRestfulService;

    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    @Autowired
    UserRestfulService userRestfulService;



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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long addOrder(long userId, String vehicleId, int beginAt, double beginLongitude, double beginDimension) throws Exception {

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

        //修改预约单上的骑行订单
        subscribeInfo info = subscribeInfoDao.getSubscribeInfoByUserId(userId, SubscribeStatus.use);
        info.setSubscribeCode(orderCode);
        subscribeInfoDao.update(info);

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

    /**
     * 修改产品信息
     *
     * @param id
     * @param productName
     * @param price
     * @param publishedPrice
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateProduct(long id, String productName, BigDecimal price, BigDecimal publishedPrice) throws Exception {
        return productDao.updateProduct(id, productName, price, publishedPrice);
    }

    /**
     * 删除产品信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int deleteById(long id) throws Exception {
        return productDao.deleteById(id);
    }

    /**
     * 增加产品
     *
     * @param product
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long insertProduct(product product) throws Exception {
        product.setProductCode("pd_recharge");
        product.setCreateAt(UnixTimeUtils.now());
        product.setCreateId(Long.valueOf(0));
        product.setUpdateAt(0);
        product.setUpdateId(Long.valueOf(0));
        return productDao.save(product);
    }

    /**
     * 获取产品列表
     *
     * @return
     */
    @Override
    public List<product> getProductList() {
        return productDao.getProductList();
    }

    @Override
    public orderItem getOrderItemByOrderCode(String orderCode) throws Exception {
        return orderItemDao.getOrderItemByOrderCode(orderCode);
    }


    /**
     * 根据主键获取用户ID
     *
     * @param id
     * @return
     */
    @Override
    public vehicleOrder getOrder(long id) throws Exception{
        return vehicleOrderDao.getOrderByid(id);
    }

    /**
     * 锁车支付
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @return
     * @throws Exception
     */
    @Override
    public UserPayIngDto userPayOrder(String bicycleCode, int endAt, double endLongitude, double endDimension) throws Exception,RestfulException {
        UserPayIngDto userPayIngDto = new UserPayIngDto();
        VehicleOrderDto dto = bicycleRestfulService.lock(bicycleCode,endAt,endLongitude,endDimension);

        if (dto != null){
            int payValue = payRestfulService.consume(dto.getOrderCode(),dto.getBeforePrice(),dto.getUserId(),0);

            if (payValue == 0){
                userPayIngDto.setRestType(1);
                VehicleOrderDto vehicleOrderDto = vehicleOrderDao.getOrderByOrderCode(dto.getOrderCode());
                vehicleOrderDto.setAmount(BigDecimal.valueOf(userRestfulService.getUserAcountMoneyByuserId(dto.getUserId())));

                userPayIngDto.setVehicleOrderDto(vehicleOrderDto);
            }else{
                userPayIngDto.setRestType(0);
                VehicleOrderDto vehicleOrderDto = vehicleOrderDao.getOrderByOrderCode(dto.getOrderCode());
                vehicleOrderDto.setAmount(BigDecimal.valueOf(userRestfulService.getUserAcountMoneyByuserId(dto.getUserId())));
                dto.setAmount(BigDecimal.valueOf(userRestfulService.getUserAcountMoneyByuserId(dto.getUserId())));
                userPayIngDto.setVehicleOrderDto(dto);
            }
        }else{
            userPayIngDto.setRestType(0);
            userPayIngDto.setVehicleOrderDto(null);
        }
        return userPayIngDto;
    }


}
