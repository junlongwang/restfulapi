package com.joybike.server.api.service;

import com.joybike.server.api.model.subscribeInfo;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface SubscribeInfoService {


    /**
     * 预约车辆
     *
     * @param userId 用户ID
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
    int updateSubscribeInfo(long userId, String vehicleId);


    /**
     * 根据预约表ID获取预约信息
     *
     * @param id
     * @return
     */
    subscribeInfo getSubscribeInfoById(long id);

    /**
     * 根据用户ID查找
     *
     * @param userId
     * @return
     */
    subscribeInfo getSubscribeInfoByUserId(long userId);

    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId);


}
