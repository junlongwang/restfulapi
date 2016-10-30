package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.SubscribeStatus;
import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.Enum.VehicleEnableType;
import com.joybike.server.api.dao.*;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

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
    private VehicleRepairDao vehicleRepairDao;

    @Autowired
    private OrderRestfulService orderRestfulService;

    @Autowired
    private VehicleOrderDao vehicleOrderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private BicycleRestfulService bicycleRestfulService;

    /**
     * 添加预约信息,如果返回的id>0则为该用户的预约ID
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public subscribeInfo vehicleSubscribe(long userId, String bicycleCode, int startAt) throws Exception {

        subscribeInfo bscribeInfo = new subscribeInfo();

        String subcribeCode = String.valueOf(userId) + String.valueOf(bicycleCode);

        subscribeInfo uInfo = subscribeInfoDao.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);
        subscribeInfo vInfo = subscribeInfoDao.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.subscribe);

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
                vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);

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
                vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
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
                vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
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
            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    public subscribeInfo getSubscribeInfoByUserId(long userId, SubscribeStatus subscribeStatus) throws Exception {
        return subscribeInfoDao.getSubscribeInfoByUserId(userId, subscribeStatus);
    }

    /**
     * 根据车辆ID获取预约信息
     *
     * @param vehicleId
     * @return
     */
    public subscribeInfo getSubscribeInfoByBicycleCode(String vehicleId, SubscribeStatus subscribeStatus) throws Exception {
        return subscribeInfoDao.getSubscribeInfoByBicycleCode(vehicleId, subscribeStatus);
    }

    /**
     * 获取车骑行记录
     *
     * @param bicycleCode
     * @param beginAt
     * @param endAt
     * @return
     */
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long addVehicleRepair(vehicleRepair vehicleRepair) throws Exception {
        vehicleRepair.setCreateAt(UnixTimeUtils.now());
        vehicleRepair.setDisposeStatus(0);
        //用户上报信息,修改车的状态为故障,待工作人员审核
        int updateStatus = updateVehicleStatus(vehicleRepair.getVehicleId(), VehicleEnableType.fault);
        long repairStatus = vehicleRepairDao.save(vehicleRepair);
        return updateStatus * repairStatus;
    }


    /**
     * 获取车辆状态
     *
     * @param bicycleCode
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public vehicle getVehicleStatusByBicycleCode(String bicycleCode) throws Exception {
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
//    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long unlock(long userId, String bicycleCode, int beginAt, double beginLongitude, double beginDimension) throws Exception {

        long orderId = 0;

        //获取车的状态
        vehicle vehicle = vehicleDao.getVehicleStatusByBicycleCode(bicycleCode);


        if (vehicle.getStatus() == 0) {

            //判断车没有被预约时
            if (vehicle.getUseStatus() == 0) {



                subscribeInfo vinfo = bicycleRestfulService.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.subscribe);
                subscribeInfo uInfo = bicycleRestfulService.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);

                //车辆跟人的预约但都存在的时候
                if (vinfo != null && uInfo != null) {
                    //判断是不是该用户的预约
                    if (vinfo.getUserId().equals(uInfo.getUserId())) {
                        //时间过期,删除重新创建
                        if (vinfo.getEndAt() - UnixTimeUtils.now() < 0) {
                            bicycleRestfulService.deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());
                            bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                            bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //开锁,通知硬件接口
//                            VehicleComHelper.openLock(bicycleCode);
                        } else {

                            //时间没有过期,直接修改
                            bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //开锁,通知硬件接口
//                            VehicleComHelper.openLock(bicycleCode);
                        }
                    }
                    //用户扫码的车辆不是预约车辆，且这辆车已经被预约，判断是该车是不是预约过期
                    if (!vinfo.getUserId().equals(uInfo.getUserId())) {
                        if (vinfo.getEndAt() - UnixTimeUtils.now() < 0) {
                            bicycleRestfulService.deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());
                            bicycleRestfulService.deleteSubscribeInfo(uInfo.getUserId(), uInfo.getVehicleId());
                            bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                            bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //开锁,通知硬件接口
//                            VehicleComHelper.openLock(bicycleCode);
                        } else {
                            throw new RestfulException(ReturnEnum.BicycleUse_Error);
                        }
                    }
                }

                //用户有预约,但车没有预约
                if (uInfo != null && vinfo == null) {
                    if (uInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                        bicycleRestfulService.deleteSubscribeInfo(uInfo.getUserId(), uInfo.getVehicleId());
                        bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                        bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                    } else {

                        //时间没有过期,直接修改
                        bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);


                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                    }
                }

                //用户没有预约,但是车被预约了
                if (uInfo == null && vinfo != null) {
                    //如果过期
                    if (vinfo.getEndAt() - UnixTimeUtils.now() < 0) {
                        bicycleRestfulService.deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());
                        bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                        bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                    } else {
                        throw new RestfulException(ReturnEnum.BicycleUse_Error);
                    }
                }

                //车与人都没有预约
                if (uInfo == null && vinfo == null) {

                    bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                    bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                    //创建订单
                    orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);


                    //修改车的使用状态
                    vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                    //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                }
            }

            //车辆被预约时
            if (vehicle.getUseStatus() == 1) {

                subscribeInfo vinfo = bicycleRestfulService.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.subscribe);
                subscribeInfo uInfo = bicycleRestfulService.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);

                //车辆跟人的预约但都存在的时候
                if (vinfo != null && uInfo != null) {
                    //判断是不是该用户的预约
                    if (vinfo.getUserId().equals(uInfo.getUserId())) {
                        //时间过期,删除重新创建
                        if (vinfo.getEndAt() - UnixTimeUtils.now() < 0) {
                            bicycleRestfulService.deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());
                            bicycleRestfulService.deleteSubscribeInfo(uInfo.getUserId(), uInfo.getVehicleId());
                            bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                            bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //开锁,通知硬件接口
//                            VehicleComHelper.openLock(bicycleCode);
                        } else {

                            //时间没有过期,直接修改
                            bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //开锁,通知硬件接口
//                            VehicleComHelper.openLock(bicycleCode);
                        }
                    }

                    //用户扫码的车辆不是预约车辆，且这辆车已经被预约，判断是该车是不是预约过期
                    if (!vinfo.getUserId().equals(uInfo.getUserId())) {
                        if (vinfo.getEndAt() - UnixTimeUtils.now() < 0) {
                            bicycleRestfulService.deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());
                            bicycleRestfulService.deleteSubscribeInfo(uInfo.getUserId(), uInfo.getVehicleId());
                            bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                            bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //开锁,通知硬件接口
//                            VehicleComHelper.openLock(bicycleCode);
                        } else {
                            throw new RestfulException(ReturnEnum.BicycleUse_Error);
                        }
                    }
                }

                //用户有预约,但车没有预约
                if (uInfo != null && vinfo == null) {
                    if (uInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                        bicycleRestfulService.deleteSubscribeInfo(uInfo.getUserId(), uInfo.getVehicleId());
                        bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                        bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);

                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                    } else {
                        //时间没有过期,直接修改
                        bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);


                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                    }
                }

                //用户没有预约,但是车被预约了
                if (uInfo == null && vinfo != null) {
                    //如果过期
                    if (vinfo.getEndAt() - UnixTimeUtils.now() < 0) {
                        bicycleRestfulService.deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());
                        bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                        bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);

                        //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                    } else {
                        throw new RestfulException(ReturnEnum.BicycleUse_Error);
                    }
                }

                //车与人都没有预约
                if (uInfo == null && vinfo == null) {

                    bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                    bicycleRestfulService.updateSubscribeInfo(userId, bicycleCode);
                    //创建订单
                    orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);


                    //修改车的使用状态
                    vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                    //开锁,通知硬件接口
//                        VehicleComHelper.openLock(bicycleCode);
                }
            }

            //当车辆状态为使用中,去找他有没有对应的单
            if (vehicle.getUseStatus() == 2) {
                subscribeInfo vinfo = bicycleRestfulService.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.use);
                subscribeInfo uInfo = bicycleRestfulService.getSubscribeInfoByUserId(userId, SubscribeStatus.use);

                if (vinfo.getUserId().equals(uInfo.getUserId())) {
                    throw new RestfulException(ReturnEnum.Use_Self_Vehicle);
                } else {
                    throw new RestfulException(ReturnEnum.Use_Vehicle);
                }

            }

            if (vehicle.getUseStatus() == -1) throw new RestfulException(ReturnEnum.FaultIng);
        }

        if (vehicle.getStatus() == 1) throw new RestfulException(ReturnEnum.Disable_Vehicle);
        if (vehicle.getStatus() == 2 || vehicle.getStatus() == -1) throw new RestfulException(ReturnEnum.FaultIng);

        return orderId;
    }

    /**
     * 锁车
     *
     * @param bicycleCode
     * @param endAt
     * @param endLongitude
     * @param endDimension
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long lock(String bicycleCode, int endAt, double endLongitude, double endDimension, long userId) throws Exception {
        //修改车状态

        subscribeInfo subscribeInfo = subscribeInfoDao.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);

        orderItem item = orderItemDao.getOrderItemByOrderCode(subscribeInfo.getSubscribeCode());

        int cyclingTime = endAt - item.getBeginAt();
        BigDecimal payPrice = payPrice(cyclingTime);

        int v1 = vehicleOrderDao.updateOrderByLock(userId, bicycleCode, payPrice);
        int o1 = orderItemDao.updateOrderByLock(userId, bicycleCode, endAt, endLongitude, endDimension, cyclingTime);
        int o2 = subscribeInfoDao.deleteSubscribeInfo(userId, bicycleCode);
        int v3 = vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.free);

        return v1 * o1 * o2 * v3;
    }

    /**
     * 修改骑行订单支付状态
     *
     * @param orderCode
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateVehicleStatausByCode(String orderCode) {
        return vehicleOrderDao.updateStatausByCode(orderCode);
    }

    /**
     * 获取用户已完成的骑行订单(支付与完成未支付的)
     *
     * @param userId
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<VehicleOrderDto> getOrderPaySuccess(long userId) throws Exception {

        List<VehicleOrderDto> list = vehicleOrderDao.getOrderPaySuccess(userId);
        if (list.size() > 0) {
            list.forEach(new Consumer<VehicleOrderDto>() {
                @Override
                public void accept(VehicleOrderDto dto) {
                    try {
                        List<vehicleHeartbeat> list = getVehicleHeartbeatList(dto.getVehicleId(), dto.getBeginAt(), dto.getEndAt());
                        dto.setVehicleHeartbeatList(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return list;
    }

    /**
     * 修改车的使用状态
     *
     * @param vehicleId
     * @param vehicleEnableType
     * @return
     */
    @Override
    public int updateVehicleStatus(String vehicleId, VehicleEnableType vehicleEnableType) throws Exception {
        return vehicleDao.updateVehicleStatus(vehicleId, vehicleEnableType);
    }


    /***************************
     * ===================
     ********************/
    public static BigDecimal payPrice(int cyclingTime) {
        int time = cyclingTime / 60;
        double t = (cyclingTime / 60) / 30;
        if (t > 0) {
            time = time + 1;
        }
        BigDecimal price = BigDecimal.valueOf(time);
        return price;
    }


}
