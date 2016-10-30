package com.joybike.server.api.service;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Enum.VehicleEnableType;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lishaoyong on 16/10/23.
 * 车的服务
 */
public interface BicycleRestfulService {

    /**
     * 预约车辆
     *
     * @param userId      用户ID
     * @param bicycleCode 车辆code
     * @return
     */
    subscribeInfo vehicleSubscribe(long userId, String bicycleCode, int startAt) throws Exception;

    /**
     * 删除车辆预约信息,两种情况，1:取消预约，2:到达15分钟预约时间
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    int deleteSubscribeInfo(long userId, String vehicleId) throws Exception;

    /**
     * 修改预约状态
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    int updateSubscribeInfo(long userId, String vehicleId) throws Exception;

    /**
     * 根据用户ID查找预约信息
     *
     * @param userId
     * @return
     */
    subscribeInfo getSubscribeInfoByUserId(long userId,SubscribeStatus subscribeStatus) throws Exception;

    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId,SubscribeStatus subscribeStatus) throws Exception;

    /**
     * 获取骑行记录
     *
     * @param bicycleCode
     * @param beginAt
     * @param endAt
     * @return
     */
    List<vehicleHeartbeat> getVehicleHeartbeatList(String bicycleCode, int beginAt, int endAt) throws Exception;


    /**
     * 故障上报
     *
     * @param vehicleRepair
     * @return
     */
    long addVehicleRepair(vehicleRepair vehicleRepair) throws Exception;

    /**
     * 获取车辆状态
     *
     * @param bicycleCode
     * @return
     */
    vehicle getVehicleStatusByBicycleCode(String bicycleCode) throws Exception;

    /**
     * 获取当前位置一公里内的车辆
     *
     * @param beginDimension
     * @param beginLongitude
     * @return
     */
    List<vehicle> getVehicleList(double beginDimension, double beginLongitude) throws Exception;

    /**
     * 解锁
     *
     * @param userId
     * @param bicycleCode
     * @param beginAt
     * @param beginLongitude
     * @param beginDimension
     */
    long unlock(long userId, String bicycleCode, int beginAt, double beginLongitude, double beginDimension) throws Exception;


    /**
     * 锁车
     *
     * @param bicycleCode
     * @param endAt
     * @param beginLongitude
     * @param beginDimension
     * @return
     */
    long lock(String bicycleCode, int endAt, double beginLongitude, double beginDimension, long userId) throws Exception;

    /**
     * 修改订单支付状态
     *
     * @param orderCode
     * @return
     */
    int updateVehicleStatausByCode(String orderCode);

    /**
     * 获取用户已完成的骑行订单(支付与完成未支付的)
     *
     * @param userId
     * @return
     */
    List<VehicleOrderDto> getOrderPaySuccess(long userId) throws Exception;


    /**
     * 修改车的使用状态
     *
     * @param vehicleId
     * @param vehicleEnableType
     * @return
     */
    int updateVehicleStatus(String vehicleId, VehicleEnableType vehicleEnableType) throws Exception;

}
