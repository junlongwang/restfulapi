package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.service.SubscribeInfoService;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lishaoyong on 16/10/20.
 */
@Service
public class SubscribeInfoServiceImpl implements SubscribeInfoService {


    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    @Autowired
    VehicleDao vehicleDao;

    /**
     * 添加预约信息,如果返回的id=0则为该用户的预约ID
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Override
    public long addSubscribeInfo(long userId, String vehicleId, int startAt) {
        subscribeInfo scribeInfo = subscribeInfoDao.getSubscribeInfo(userId, vehicleId);
        if (scribeInfo != null) {
            return 0;
        } else {
            //预约信息插入
            subscribeInfo info = new subscribeInfo();
            info.setUserId(userId);
            info.setVehicleId(vehicleId);
            info.setStartAt(startAt);
            info.setEndAt(startAt + 900);
            info.setCreateAt(UnixTimeUtils.now());
            info.setStatus(SubscribeStatus.subscribe.getValue());
            info.setUpdateAt(0);
            long subscribeId = subscribeInfoDao.save(info);

            //修改车辆使用状态
            vehicleDao.updateVehicleStatus(vehicleId, UseStatus.subscribe);
            return subscribeId;
        }
    }

    /**
     * 删除车辆预约信息,两种情况，1:取消预约，2:到达15分钟预约时间
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Override
    public int deleteSubscribeInfo(long userId, String vehicleId) {
        return subscribeInfoDao.deleteSubscribeInfo(userId, vehicleId);
    }

    /**
     * 修改预约状态，扫码后修改车辆预约状态为使用中
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Override
    public int updateSubscribeInfo(long userId, String vehicleId) {
        return subscribeInfoDao.updateSubscribeInfo(userId, vehicleId, SubscribeStatus.use);
    }

    /**
     * 根据车辆code获取当前使用车的用户ID
     *
     * @param vehicleId
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoByVehicleId(String vehicleId, SubscribeStatus subscribeStatus) {
        return subscribeInfoDao.getSubscribeInfoByVehicleId(vehicleId, subscribeStatus);
    }

    /**
     * 根据用户与车辆信息获取预约信息
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfo(long userId, String vehicleId) {
        return subscribeInfoDao.getSubscribeInfo(userId, vehicleId);
    }

    /**
     * 根据用户ID查找
     *
     * @param userId
     * @param subscribeStatus
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoByUserId(long userId, SubscribeStatus subscribeStatus) {
        return subscribeInfoDao.getSubscribeInfoByUserId(userId, subscribeStatus);
    }


}
