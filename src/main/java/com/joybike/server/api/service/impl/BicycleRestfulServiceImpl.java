package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.*;
import com.joybike.server.api.dao.*;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.dto.VehicleOrderSubscribeDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import com.joybike.server.api.thirdparty.amap.AMapUtil;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class BicycleRestfulServiceImpl implements BicycleRestfulService {

    private final Logger logger = Logger.getLogger(BicycleRestfulServiceImpl.class);

    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    @Autowired
    private VehicleDao vehicleDao;

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
    private BankAcountDao bankAcountDao;

    /**
     * 添加预约信息,如果返回的id>0则为该用户的预约ID
     * 预约的逻辑调整,用户只会看到当前空闲的车,只需要判断用户是不是预约了其他车或者上一预约有没有过期
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public subscribeInfo vehicleSubscribe(long userId, String bicycleCode, int startAt) throws Exception {


        subscribeInfo bscribeInfo = new subscribeInfo();

        subscribeInfo subscribeInfo = subscribeInfoDao.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);
        String subcribeCode = String.valueOf(userId) + String.valueOf(bicycleCode);
        vehicle vehicle = vehicleDao.getVehicleByCode(bicycleCode);

        if (vehicle.getUseStatus() == 0) {
            if (subscribeInfo != null) {
                //过期,释放原车,修改车辆状态
                if (subscribeInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                    //过期,释放原车
                    vehicleDao.updateVehicleUseStatus(subscribeInfo.getVehicleId(), UseStatus.subscribe);
                    subscribeInfoDao.delete(subscribeInfo.getId());
                    subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode, vehicle.getLastDimension(), vehicle.getLastLongitude());
                    long subscribeId = subscribeInfoDao.save(info);
                    bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);
                    vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
                } else {
                    throw new RestfulException(ReturnEnum.Repeat_Error);
                }
            }

            if (subscribeInfo == null) {
                subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode, vehicle.getLastDimension(), vehicle.getLastLongitude());
                long subscribeId = subscribeInfoDao.save(info);
                bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);
                vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
            }
        }
        //这种场景不会出现,但为了幂等
        if (vehicle.getUseStatus() == 1) {
            subscribeInfo vInfo = subscribeInfoDao.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.subscribe);
            if (vInfo.getUserId().equals(userId)) {
                //过期,释放原车,修改车辆状态
                if (subscribeInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                    //过期,释放原车
                    vehicleDao.updateVehicleUseStatus(subscribeInfo.getVehicleId(), UseStatus.subscribe);
                    subscribeInfoDao.delete(subscribeInfo.getId());
                    subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode, vehicle.getLastDimension(), vehicle.getLastLongitude());
                    long subscribeId = subscribeInfoDao.save(info);
                    bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);
                    vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
                } else {
                    throw new RestfulException(ReturnEnum.Repeat_Error);
                }
            }

            if (!vInfo.getUserId().equals(userId)) {
                //过期,释放原车,修改车辆状态
                if (vInfo.getEndAt() - UnixTimeUtils.now() < 0) {
                    //过期,释放原车
                    vehicleDao.updateVehicleUseStatus(vInfo.getVehicleId(), UseStatus.subscribe);
                    subscribeInfoDao.delete(vInfo.getId());
                    subscribeInfo info = insertSubscribeInfo(userId, bicycleCode, startAt, SubscribeStatus.subscribe, subcribeCode, vehicle.getLastDimension(), vehicle.getLastLongitude());
                    long subscribeId = subscribeInfoDao.save(info);
                    bscribeInfo = subscribeInfoDao.getSubscribeInfoById(subscribeId);
                    vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.subscribe);
                } else {
                    throw new RestfulException(ReturnEnum.BicycleUse_Error);
                }
            }
        }

        return bscribeInfo;
    }

    //保存预约信息
    public static subscribeInfo insertSubscribeInfo(long userId, String bicycleCode, int startAt, SubscribeStatus subscribeStatus, String subcribeCode, BigDecimal dimension, BigDecimal longitude) {
        subscribeInfo info = new subscribeInfo();
        info.setUserId(userId);
        info.setVehicleId(bicycleCode);
        info.setStartAt(startAt);
        info.setEndAt(startAt + 900);
        info.setCreateAt(UnixTimeUtils.now());
        info.setStatus(subscribeStatus.getValue());
        info.setSubscribeCode(subcribeCode);
        info.setDimension(dimension);
        info.setLongitude(longitude);
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
        return vehicleDao.updateVehicleUseStatus(vehicleId, UseStatus.free) * subscribeInfoDao.deleteSubscribeInfo(userId, vehicleId);

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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public VehicleOrderDto unlock(long userId, String bicycleCode, int beginAt, double beginLongitude, double beginDimension) throws Exception {

        VehicleOrderDto dto = new VehicleOrderDto();

        vehicle vehicle = vehicleDao.getVehicleStatusByBicycleCode(bicycleCode);

        Boolean isLock = isUnLock(userId, bicycleCode);
        if (isLock) {

            VehicleComHelper.openLock(vehicle.getBundlingPhone());
            //Thread.sleep(10*1000);
            //锁的状态是锁车状态

            long timout = System.currentTimeMillis()+40*1000;
            while (!"1".equals(RedixUtil.getString(vehicle.getLockId().toString()))) {
                logger.info(vehicle.getLockId() + "等待车辆开锁...............");
                Thread.sleep(1000);
                if(System.currentTimeMillis()>timout) {
                     throw new RestfulException(ReturnEnum.Unlock_TIMEOUT_Error);
                }
                continue;
            }

            logger.info(vehicle.getLockId() + "收到车辆开锁状态:" + RedixUtil.getString(vehicle.getLockId().toString()));

            if (vehicle.getStatus() == 0) {

                //车空闲时
                if (vehicle.getUseStatus() == 0) {

                    subscribeInfo uInfo = getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);

                    //用户有预约
                    if (uInfo != null) {
                        subscribeInfoDao.delete(uInfo.getId());
                        vehicleDao.updateVehicleUseStatus(uInfo.getVehicleId(), UseStatus.free);
                        vehicleSubscribe(userId, bicycleCode, beginAt);
                        updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        long orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        dto = getOrderInfo(orderId);
                    }

                    //没有预约
                    if (uInfo == null) {
                        vehicleSubscribe(userId, bicycleCode, beginAt);
                        updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        long orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        dto = getOrderInfo(orderId);
                    }
                }

                //车辆被预约时
                if (vehicle.getUseStatus() == 1) {

                    subscribeInfo uInfo = getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);
                    subscribeInfo vinfo = getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.subscribe);

                    if (uInfo.getUserId().equals(vinfo.getUserId())) {
                        updateSubscribeInfo(userId, bicycleCode);
                        //创建订单
                        long orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
                        //修改车的使用状态
                        vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                        //订单信息
                        dto = getOrderInfo(orderId);
                    }
                    if (!uInfo.getUserId().equals(vinfo.getUserId())) {
                        if (vinfo.getEndAt() - UnixTimeUtils.now() > 0) {
                            throw new RestfulException(ReturnEnum.BicycleUse_Error);
                        } else {
                            vehicleDao.updateVehicleUseStatus(vinfo.getVehicleId(), UseStatus.free);
                            vehicleDao.updateVehicleUseStatus(uInfo.getVehicleId(), UseStatus.free);

                            deleteSubscribeInfo(uInfo.getUserId(), uInfo.getVehicleId());
                            deleteSubscribeInfo(vinfo.getUserId(), vinfo.getVehicleId());

                            vehicleSubscribe(userId, bicycleCode, beginAt);
                            updateSubscribeInfo(userId, bicycleCode);
                            //创建订单
                            long orderId = orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
                            //修改车的使用状态
                            vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.use);
                            //订单信息
                            dto = getOrderInfo(orderId);
                        }
                    }
                }

                if (vehicle.getUseStatus() == 2) {
                    subscribeInfo vinfo = getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.use);

                    if (vinfo.getUserId().equals(userId)) {
                        throw new RestfulException(ReturnEnum.Use_Self_Vehicle);
                    } else {
                        throw new RestfulException(ReturnEnum.Use_Vehicle);
                    }
                }

                if (vehicle.getUseStatus() == -1) throw new RestfulException(ReturnEnum.FaultIng);
            }

            if (vehicle.getStatus() == 1) throw new RestfulException(ReturnEnum.Disable_Vehicle);
            if (vehicle.getStatus() == 2 || vehicle.getStatus() == -1)
                throw new RestfulException(ReturnEnum.FaultIng);

        } else {
            throw new RestfulException(ReturnEnum.Unlock_Error);
        }

        return dto;
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
    public VehicleOrderDto lock(String bicycleCode, int endAt, double endLongitude, double endDimension) throws Exception {

        logger.info("开始锁车！");
        //修改车状态
        List<subscribeInfo> list = subscribeInfoDao.getSubscribeInfoByBicycleCodeList(bicycleCode, SubscribeStatus.use);
        VehicleOrderDto dto = new VehicleOrderDto();
        logger.info("获取信息条数" + list.size());
        if (list != null && list.size() > 0) {
            for (int i = 0; i <= list.size() - 1; i++) {
                subscribeInfo subscribeInfo = list.get(i);
                logger.info(subscribeInfo);
                if (subscribeInfo != null) {
                    try {
                        vehicleOrder order = orderRestfulService.getOrderByVehicleId(subscribeInfo.getVehicleId());
                        logger.info(order);

                        if (order != null) {
                            if (order.getOrderCode().equals(subscribeInfo.getSubscribeCode())) {
                                orderItem item = orderItemDao.getOrderItemByOrderCode(subscribeInfo.getSubscribeCode());
                                logger.info("锁车的时候" + bicycleCode + ":" + endAt + ":" + endLongitude + ":" + endDimension);
                                int cyclingTime = endAt - item.getBeginAt();
                                //计算金额
                                BigDecimal payPrice = payPrice(cyclingTime);

                                logger.info("消费金额:" + payPrice);

                                int v1 = vehicleOrderDao.updateOrderByLock(subscribeInfo.getUserId(), payPrice, order.getOrderCode());
                                logger.info("修改订单的状态:" + v1);
                                int o1 = orderItemDao.updateOrderByLock(subscribeInfo.getUserId(), endAt, endLongitude, endDimension, cyclingTime, AMapUtil.getAddress(endLongitude + "," + endDimension), order.getOrderCode(), bicycleCode);
                                logger.info("修改ITEM的状态:" + o1);
                                int v3 = vehicleDao.updateVehicleUseStatus(bicycleCode, UseStatus.free);
                                logger.info("修改车的状态:" + v3);

                                if (v1 * o1 * v3 > 0) {
                                    dto = vehicleOrderDao.getOrderByOrderCode(subscribeInfo.getSubscribeCode());
                                } else {
                                    return null;
                                }
                            } else {
                                continue;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    throw new RestfulException(ReturnEnum.NoPay);
                }
            }
        }
        return dto;
    }

    /**
     * 修改骑行订单支付状态
     *
     * @param orderCode
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateVehicleStatausByCode(String orderCode, long payId) throws Exception {
        return vehicleOrderDao.updateStatausByCode(orderCode, payId);
    }

    /**
     * 获取用户已完成的骑行订单(支付与完成未支付的)与形成记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<VehicleOrderDto> getOrderPaySuccess(long userId) throws Exception {

        List<VehicleOrderDto> list = vehicleOrderDao.getOrderPaySuccess(userId);
        if (list != null && list.size() > 0) {
            list.stream().sorted((p, p2) -> (p.getEndAt().compareTo(p2.getEndAt()))).collect(toList());
        }
        return list;
    }

    /**
     * 获取用户已完成的骑行订单(支付与完成未支付的)与形成记录
     *
     * @param userId
     * @return
     */
    @Override
    public VehicleOrderDto getLastSuccessOrder(long userId) throws Exception {

        VehicleOrderDto dto = vehicleOrderDao.getLastOrderPaySuccess(userId);
        dto.setAmount(BigDecimal.valueOf(bankAcountDao.getUserAmount(userId)));

        return dto;
    }

    /**
     * 是否可以解锁
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @Override
    public Boolean isUnLock(long userId, String bicycleCode) throws Exception {
        //逻辑优化调整,人没有未支付订单，车状态正常,解锁,其他不关注

        vehicle vehicle = vehicleDao.getVehicleStatusByBicycleCode(bicycleCode);

        Boolean isLock = false;

        if (vehicle.getStatus() == 1 || vehicle.getStatus() == 2) {
            isLock = false;
        }

        if (vehicle.getStatus() == 0) {
            if (vehicle.getUseStatus() == 0) {
                isLock = true;
            }

            if (vehicle.getUseStatus() == 1) {
                subscribeInfo subscribeInfo = subscribeInfoDao.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.subscribe);

                if (subscribeInfo.getUserId().equals(userId)) {
                    isLock = true;
                }
                if (!subscribeInfo.getUserId().equals(userId)) {
                    if (subscribeInfo.getEndAt() - UnixTimeUtils.now() > 0) {
                        throw new RestfulException(ReturnEnum.BicycleUse_Error);
                    } else {
                        isLock = true;
                    }
                }
            }

            if (vehicle.getUseStatus() == 2) {
                subscribeInfo vInfo = subscribeInfoDao.getSubscribeInfoByBicycleCode(bicycleCode, SubscribeStatus.use);
                if (vInfo.getUserId().equals(userId)) {
                    throw new RestfulException(ReturnEnum.Use_Self_Vehicle);
                } else {
                    throw new RestfulException(ReturnEnum.Use_Vehicle);
                }
            }

        }
        return isLock;
    }


    @Override
    public void deleteByExpire() throws Exception {

        List<subscribeInfo> list = subscribeInfoDao.getByTime();


        if (list != null) {
            list.forEach(new Consumer<subscribeInfo>() {
                @Override
                public void accept(subscribeInfo subscribeInfo) {
                    try {
                        logger.info(subscribeInfo);
                        vehicleDao.updateVehicleUseStatus(subscribeInfo.getVehicleId(), UseStatus.free);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        subscribeInfoDao.deleteByExpire();
    }


    /**
     * 修改车的使用状态
     *
     * @param vehicleId
     * @param vehicleEnableType
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateVehicleStatus(String vehicleId, VehicleEnableType vehicleEnableType) throws Exception {
        return vehicleDao.updateVehicleStatus(vehicleId, vehicleEnableType);
    }

    /**
     * 订单信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public VehicleOrderDto getOrderInfo(long id) throws Exception {
        VehicleOrderDto dto = vehicleOrderDao.getOrderById(id);

        if (dto != null) {
            if (dto.getStatus() == 1) {
                dto.setEndAt(UnixTimeUtils.now());
                List<vehicleHeartbeat> list = getVehicleHeartbeatList(dto.getVehicleId(), dto.getBeginAt(), dto.getEndAt());
                if (list.size() > 0) {
                    dto.setVehicleHeartbeatList(list);
                }
            }

        }
        int runTime = UnixTimeUtils.now() - dto.getBeginAt();
        if (runTime < 120) {
            dto.setBeforePrice(BigDecimal.valueOf(0));
        } else {
            BigDecimal price = payPrice(runTime);
            dto.setBeforePrice(price);
        }

        dto.setCyclingTime(runTime);

        return dto;
    }

    /**
     * 使用信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public VehicleOrderSubscribeDto getUseInfo(long userId) throws Exception {
        VehicleOrderDto vehicleOrderDto = vehicleOrderDao.getOrderByUserId(userId);
        subscribeInfo subscribeInfo = subscribeInfoDao.getSubscribeInfoByUserId(userId, SubscribeStatus.subscribe);

        VehicleOrderSubscribeDto dto = new VehicleOrderSubscribeDto();

        if (vehicleOrderDto != null) {
            dto.setVehicleOrderDto(vehicleOrderDto);
        } else if (subscribeInfo != null) {
            if (subscribeInfo.getEndAt() - UnixTimeUtils.now() >= 0) {
                dto.setInfo(subscribeInfo);
            }
        }
        return dto;
    }


    /***************************
     * ===================
     ********************/
    public static BigDecimal payPrice(int cyclingTime) {

        if (cyclingTime < 120) {
            return BigDecimal.valueOf(0);
        } else {
            int time = cyclingTime / 60;
            double t = time / 30;
            if (t >= 0) {
                t = t + 1;
            }
            BigDecimal price = BigDecimal.valueOf(t);
            return price;
        }

    }

    public int updateVehicleImg(String vehicleId, String vehicleImg, String remark) throws Exception {
        return vehicleDao.updateVehicleImg(vehicleId, vehicleImg, remark);
    }

}
