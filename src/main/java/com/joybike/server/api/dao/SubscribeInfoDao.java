package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.subscribeInfo;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface SubscribeInfoDao extends IRepository<subscribeInfo> {

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
     * @param subscribeStatus
     * @return
     */
    int updateSubscribeInfo(long userId, String vehicleId, SubscribeStatus subscribeStatus) throws Exception;

    /**
     * 根据预约表ID获取预约信息
     *
     * @param id
     * @return
     */
    subscribeInfo getSubscribeInfoById(long id) throws Exception;

    /**
     * 根据用户ID查找
     *
     * @param userId
     * @param status
     * @return
     */
    subscribeInfo getSubscribeInfoByUserId(long userId,SubscribeStatus status) throws Exception;


    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId,SubscribeStatus status) throws Exception;


    int deleteByExpire() throws Exception;

    int deleteByOrderCode(String orderCode);

    List<subscribeInfo> getSubscribeInfoByBicycleCodeList(String vehicleId,SubscribeStatus status) throws Exception;
}
