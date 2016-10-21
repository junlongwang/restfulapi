package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.subscribeInfo;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface SubscribeInfoDao extends IRepository<subscribeInfo> {


    /**
     * 根据用户,车辆ID获取预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    subscribeInfo getSubscribeInfo(long userId, String vehicleId);

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
     * @param subscribeStatus
     * @return
     */
    int updateSubscribeInfo(long userId, String vehicleId, SubscribeStatus subscribeStatus);

    /**
     * 根据车辆code获取该车使用用户
     *
     * @param vehicleId
     * @param subscribeStatus
     * @return
     */
    subscribeInfo getSubscribeInfoByVehicleId(String vehicleId, SubscribeStatus subscribeStatus);

    /**
     * 根据用户ID查找
     *
     * @param userId
     * @param subscribeStatus
     * @return
     */
    subscribeInfo getSubscribeInfoByUserId(long userId, SubscribeStatus subscribeStatus);

}
