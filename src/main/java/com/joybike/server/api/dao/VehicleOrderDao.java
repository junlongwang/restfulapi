package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.vehicleOrder;

import java.math.BigDecimal;
import java.util.List;

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
    int updateStatausByCode(String orderCode) throws Exception;


    /**
     * 根据用户ID获取未完成订单
     */
    vehicleOrder getNoPayByOrder(long userId, String orderCode) throws Exception;


    /**
     * 获取用户已完成的骑行订单(支付与完成未支付的)
     *
     * @param userId
     * @return
     */
    List<VehicleOrderDto> getOrderPaySuccess(long userId) throws Exception;

    /**
     * 根据主键获取订单信息
     */
    vehicleOrder getOrderByid(long id) throws Exception;

    /**
     * 根据订单Id获取订单信息
     *
     * @param id
     * @return
     */
    VehicleOrderDto getOrderById(long id) throws Exception;

    /**
     * 根据用户获取
     *
     * @param userId
     * @return
     * @throws Exception
     */
    VehicleOrderDto getOrderByUserId(long userId) throws Exception;

    /**
     * 根据code获取订单信息
     *
     * @param orderCode
     * @return
     * @throws Exception
     */
    VehicleOrderDto getOrderByOrderCode(String orderCode) throws Exception;
}
