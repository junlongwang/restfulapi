package com.joybike.server.api.service;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.model.subscribeInfo;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface SubscribeInfoService {


    /**
     * 添加预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    long addSubscribeInfo(long userId, String vehicleId, int startAt);

    /**
     * 删除车辆预约信息,两种情况，1:取消预约，2:到达15分钟预约时间
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    int deleteSubscribeInfo(long userId, String vehicleId);

    /**
     * 修改预约状态
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    int updateSubscribeInfo(long userId, String vehicleId);

    /**
     * 根据车辆code 获取当前那个用户在使用该车
     *
     * @param vehicleId
     * @return
     */
    subscribeInfo getSubscribeInfoByVehicleId(String vehicleId, SubscribeStatus subscribeStatus);

    /**
     * 根据用户与车辆ID获取预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    subscribeInfo getSubscribeInfo(long userId, String vehicleId);

    /**
     * 根据用户ID查找
     *
     * @param userId
     * @param subscribeStatus
     * @return
     */
    subscribeInfo getSubscribeInfoByUserId(long userId, SubscribeStatus subscribeStatus);
}
