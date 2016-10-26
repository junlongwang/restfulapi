package com.joybike.server.api.service;


import com.joybike.server.api.model.product;
import com.joybike.server.api.model.vehicleOrder;

import java.math.BigDecimal;
import java.util.List;

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
    long addOrder(long userId, String vehicleId, int beginAt, double beginLongitude , double beginDimension) throws Exception;


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


    /**
     * 修改产品信息
     *
     * @param id
     * @param productName
     * @param price
     * @param publishedPrice
     * @return
     */
    int updateProduct(long id, String productName, BigDecimal price, BigDecimal publishedPrice) throws Exception;


    /**
     * 删除产品
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
     * @return
     */
    List<product> getProductList();
}
