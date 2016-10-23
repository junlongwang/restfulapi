package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.model.Message;
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
     * 添加预约信息,如果返回的id>0则为该用户的预约ID
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Override
    public long VehicleSubscribe(long userId, String vehicleId, int startAt) throws Exception {

        /**
         * 预约的逻辑调整为，
         * 1：如果该车有预约,且过期时间为未过期,则返回不可预约
         * 2：如果该车有预约,但过期时间已到并且不为待支付状态，则删除原预约信息
         * 3: 如果该车没有预约信息,则添加预约信息
         */
        String subcribeCode = String.valueOf(userId) + String.valueOf(vehicleId);
        subscribeInfo vInfo = subscribeInfoDao.getSubscribeInfo(vehicleId);
        subscribeInfo uInfo = subscribeInfoDao.getSubscribeInfoByUserId(userId);


        if (vInfo != null && uInfo != null) {
            if (vInfo.getStatus() == SubscribeStatus.nComplete.getValue()) {
                return 0;

            }else if (vInfo.getStatus() == SubscribeStatus.subscribe.getValue()){
                //删除原订单
                subscribeInfoDao.delete(uInfo.getId());
                //预约信息插入
                subscribeInfo info = new subscribeInfo();
                info.setUserId(userId);
                info.setVehicleId(vehicleId);
                info.setStartAt(startAt);
                info.setEndAt(startAt + 900);
                info.setCreateAt(UnixTimeUtils.now());
                info.setStatus(SubscribeStatus.subscribe.getValue());
                info.setUpdateAt(0);
                info.setSubscribeCode(subcribeCode);
                long subscribeId = subscribeInfoDao.save(info);
                //修改车辆使用状态
                vehicleDao.updateVehicleStatus(vehicleId, UseStatus.subscribe);
                return subscribeId;


            }
        } else if (vInfo != null) {
            return 0;
        } else if (uInfo != null) {
            //删除原订单
            subscribeInfoDao.delete(uInfo.getId());
            //预约信息插入
            subscribeInfo info = new subscribeInfo();
            info.setUserId(userId);
            info.setVehicleId(vehicleId);
            info.setStartAt(startAt);
            info.setEndAt(startAt + 900);
            info.setCreateAt(UnixTimeUtils.now());
            info.setStatus(SubscribeStatus.subscribe.getValue());
            info.setUpdateAt(0);
            info.setSubscribeCode(subcribeCode);
            long subscribeId = subscribeInfoDao.save(info);
            //修改车辆使用状态
            vehicleDao.updateVehicleStatus(vehicleId, UseStatus.subscribe);
            return subscribeId;
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
            info.setSubscribeCode(subcribeCode);
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
        return subscribeInfoDao.getSubscribeInfo(vehicleId);
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
        return subscribeInfoDao.getSubscribeInfoByUserId(userId);
    }


}
