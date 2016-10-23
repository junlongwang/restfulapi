package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.model.Message;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.service.SubscribeInfoService;
import com.joybike.server.api.util.RestfulException;
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
     * @param bicycleCode
     * @return
     */
    @Override
    public subscribeInfo vehicleSubscribe(long userId, String bicycleCode, int startAt) throws Exception {

        subscribeInfo bscribeInfo = new subscribeInfo();
        String subcribeCode = String.valueOf(userId) + String.valueOf(bicycleCode);
        subscribeInfo uInfo = subscribeInfoDao.getSubscribeInfoByUserId(userId);
        subscribeInfo vInfo = subscribeInfoDao.getSubscribeInfoByBicycleCode(bicycleCode);

        if (vInfo != null && uInfo != null) {
            if (vInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                //删除原订单
                subscribeInfoDao.delete(uInfo.getId());
                subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode);
                //保存预约信息
                long subscribeId = subscribeInfoDao.save(info);
                //返回预约信息
                bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);

                //修改车辆使用状态
                vehicleDao.updateVehicleStatus(bicycleCode, UseStatus.subscribe);

            } else {
                throw new RestfulException("1001:" + "不可重复预约");
//                bscribeInfo = vInfo;
            }
        }

        if (uInfo != null && vInfo == null) {
            if (uInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                //删除原订单
                subscribeInfoDao.delete(uInfo.getId());
                subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode);
                //保存预约信息
                long subscribeId = subscribeInfoDao.save(info);
                //返回预约信息
                bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);

                //修改车辆使用状态
                vehicleDao.updateVehicleStatus(bicycleCode, UseStatus.subscribe);
            } else {
//                bscribeInfo = uInfo;
                throw new RestfulException("1001:" + "不可重复预约");
            }
        }

        if (vInfo != null && uInfo == null) {
            if (vInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode);
                //保存预约信息
                long subscribeId = subscribeInfoDao.save(info);
                //返回预约信息
                bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);

                //修改车辆使用状态
                vehicleDao.updateVehicleStatus(bicycleCode, UseStatus.subscribe);
            } else {
                throw new RestfulException("1001:" + "该车已被预约");
            }
        }

        if (vInfo == null && uInfo == null) {
            subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode);
            //保存预约信息
            long subscribeId = subscribeInfoDao.save(info);
            //返回预约信息
            bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);

            //修改车辆使用状态
            vehicleDao.updateVehicleStatus(bicycleCode, UseStatus.subscribe);
        }
        return bscribeInfo;
    }

    //保存预约信息
    public static subscribeInfo insertSubscribeInfo(long userId, String bicycleCode, int startAt, SubscribeStatus subscribeStatus, String subcribeCode) {
        subscribeInfo info = new subscribeInfo();
        info.setUserId(userId);
        info.setVehicleId(bicycleCode);
        info.setStartAt(startAt);
        info.setEndAt(startAt + 900);
        info.setCreateAt(UnixTimeUtils.now());
        info.setStatus(subscribeStatus.getValue());
        info.setUpdateAt(0);
        info.setSubscribeCode(subcribeCode);
        return info;
    }

    /**
     * 删除车辆预约信息,两种情况，1:取消预约，2:到达15分钟预约时间
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Override
    public int deleteSubscribeInfo(long userId, String vehicleId) throws Exception{
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
     * 根据ID获取预约信息
     *
     * @param id
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoById(long id) {
        return subscribeInfoDao.getSubscribeInfoById(id);
    }

    /**
     * 根据用户获取预约信息
     *
     * @param userId
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoByUserId(long userId) {
        return subscribeInfoDao.getSubscribeInfoByUserId(userId);
    }

    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId) {
        return subscribeInfoDao.getSubscribeInfoByBicycleCode(vehicleId);
    }

}
