package com.joybike.server.api.service;


import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.model.bankDepositOrder;
import com.joybike.server.api.model.orderItem;
import com.joybike.server.api.model.product;
import com.joybike.server.api.model.vehicleOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/23.
 */
public interface OrderRestfulService {

    /**
     * 创建订单
     *
     * @param userId          户ID
     * @param vehicleId      车身印刷ID
     * @param beginAt        开始
     * @param beginLongitude  行开始 经度
     * @param beginDimension  行开始 维度
     * @return
     */
    long addOrder(long userId, String vehicleId, int beginAt, double beginLongitude, double beginDimension) throws Exception;


    /**
     *  据 户ID获取 户 完成 订单
     *
     * @param userId
     * @return
     */
    vehicleOrder getNoPayOrderByUserId(long userId) throws Exception;

    /**
     *  据车辆ID获取 户 完成订单
     *
     * @param vehicleId
     * @return
     */
    vehicleOrder getOrderByVehicleId(String vehicleId) throws Exception;


    /**
     * 修 产品信息
     *
     * @param id
     * @param productName
     * @param price
     * @param publishedPrice
     * @return
     */
    int updateProduct(long id, String productName, BigDecimal price, BigDecimal publishedPrice) throws Exception;


    /**
     * 删 产品
     *
     * @param id
     * @return
     */
    int deleteById(long id) throws Exception;

    /**
     * 增加产品
     *
     * @param product
     * @return
     */
    long insertProduct(product product) throws Exception;

    /**
     * 获取产品列表
     *
     * @return
     */
    List<product> getProductList();

    /**
     * 获取 户订单资
     *
     * @param orderCode
     * @return
     * @throws Exception
     */
    orderItem getOrderItemByOrderCode(String orderCode) throws Exception;

    /**
     * 获取订单信息
     *
     * @param id
     * @return
     */
    vehicleOrder getOrder(long id) throws Exception;


    /**
     *锁车支付
     *
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @return
     * @throws Exception
     */
    UserPayIngDto userPayOrder(String bicycleCode, int endAt, double endLongitude, double endDimension) throws Exception;
}
