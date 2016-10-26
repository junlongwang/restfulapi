package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dao.VehicleRepairDao;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class BicycleRestfulServiceImpl implements BicycleRestfulService {

    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    @Autowired
    VehicleDao vehicleDao;

    @Autowired
    private VehicleHeartbeatDao vehicleHeartbeatDao;

    @Autowired
    private BicycleRestfulService bicycleRestfulService;

    @Autowired
    private VehicleRepairDao vehicleRepairDao;

    @Autowired
    private OrderRestfulService orderRestfulService;


    /**
     * 添加预约信息,如果返回的id>0则为该用户的预约ID
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @Transactional
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
                throw new RestfulException(ReturnEnum.Repeat_Error);
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
                throw new RestfulException(ReturnEnum.Repeat_Error);
            }
        }

        if (uInfo == null && vInfo != null) {
            if (vInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode);
                //保存预约信息
                long subscribeId = subscribeInfoDao.save(info);
                //返回预约信息
                bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);

                //修改车辆使用状态
                vehicleDao.updateVehicleStatus(bicycleCode, UseStatus.subscribe);
            } else {
                throw new RestfulException(ReturnEnum.BicycleUse_Error);
            }
        }

        if (uInfo == null && vInfo == null) {
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
    @Transactional
    @Override
    public int deleteSubscribeInfo(long userId, String vehicleId) throws Exception {

        return subscribeInfoDao.deleteSubscribeInfo(userId, vehicleId);

    }

    /**
     * 修改预约状态，扫码后修改车辆预约状态为使用中
     *
     * @param userId
     * @param vehicleId
     * @return
     */
    @Transactional
    @Override
    public int updateSubscribeInfo(long userId, String vehicleId) throws Exception {
        return subscribeInfoDao.updateSubscribeInfo(userId, vehicleId, SubscribeStatus.use);
    }

    /**
     * 根据用户获取预约信息
     *
     * @param userId
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoByUserId(long userId) throws Exception {
        return subscribeInfoDao.getSubscribeInfoByUserId(userId);
    }

    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    @Override
    public subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId) throws Exception {
        return subscribeInfoDao.getSubscribeInfoByBicycleCode(vehicleId);
    }

    /**
     * 获取车骑行记录
     *
     * @param bicycleCode
     * @param beginAt
     * @param endAt
     * @return
     */
    @Override
    public List<vehicleHeartbeat> getVehicleHeartbeatList(String bicycleCode, int beginAt, int endAt) throws Exception {
        long lockId = vehicleDao.getLockByBicycleCode(bicycleCode);
        return vehicleHeartbeatDao.getVehicleHeartbeatList(lockId, beginAt, endAt);
    }

    /**
     * 故障上报
     *
     * @param vehicleRepair
     * @return
     */
    @Transactional
    @Override
    public long addVehicleRepair(vehicleRepair vehicleRepair) throws Exception {
        vehicleRepair.setCreateAt(UnixTimeUtils.now());
        vehicleRepair.setDisposeStatus(0);
        return vehicleRepairDao.save(vehicleRepair);
    }


    /**
     * 获取车辆使用状态
     *
     * @param bicycleCode
     * @return
     */
    @Override
    public int getVehicleUseStatusByBicycleCode(String bicycleCode) throws Exception {
        return vehicleDao.getVehicleUseStatusByBicycleCode(bicycleCode);
    }

    /**
     * 获取车辆本身的状态
     *
     * @param bicycleCode
     * @return
     * @throws Exception
     */
    @Override
    public int getVehicleStatusByBicycleCode(String bicycleCode) throws Exception {
        return vehicleDao.getVehicleStatusByBicycleCode(bicycleCode);
    }

    /**
     * 获取当前位置一公里内的车辆
     *
     * @param beginDimension
     * @param beginLongitude
     * @return
     */
    @Override
    public List<vehicle> getVehicleList(double beginDimension, double beginLongitude) throws Exception {
        return vehicleDao.getVehicleList(beginDimension, beginLongitude);
    }

    /**
     * 解锁
     *
     * @param userId
     * @param bicycleCode
     * @param beginAt
     * @param beginLongitude
     * @param beginDimension
     */
    @Transactional
    @Override
    public long unlock(long userId, String bicycleCode, int beginAt, double beginLongitude, double beginDimension) throws Exception {

        long orderId = 0;

        //获取使用状态
        int useStatus = vehicleDao.getVehicleUseStatusByBicycleCode(bicycleCode);

        //获取车的状态
        int status = vehicleDao.getVehicleStatusByBicycleCode(bicycleCode);
        if (status == 0) {
            if (useStatus == 1) throw new RestfulException(ReturnEnum.BicycleUse_Error);
            if (useStatus == 2) throw new RestfulException(ReturnEnum.Use_Vehicle);
            if (useStatus == 0) {
                //开锁
                VehicleComHelper.openLock(bicycleCode);
                //修改车的状态
                vehicleDao.updateVehicleStatus(bicycleCode, UseStatus.use);
                //创建订单
                orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

            }
            if (useStatus == -1) throw new RestfulException(ReturnEnum.FaultIng);
        }
        if (status == 1) throw new RestfulException(ReturnEnum.Disable_Vehicle);
        if (status == 2) throw new RestfulException(ReturnEnum.FaultIng);
        if (status == -1) throw new RestfulException(ReturnEnum.FaultIng);

        return orderId;
    }

    /**
     * 锁车
     *
     * @param bicycleCode
     * @param endAt
     * @param beginLongitude
     * @param beginDimension
     * @return
     */
    @Override
    public long lock(String bicycleCode, int endAt, double beginLongitude, double beginDimension) throws Exception{
        //修改车状态
        vehicleDao.updateVehicleStatus(bicycleCode , UseStatus.free);
        return 0;
    }

}
