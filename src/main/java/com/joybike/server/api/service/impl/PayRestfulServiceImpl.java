package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.*;
import com.joybike.server.api.dao.*;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class PayRestfulServiceImpl implements PayRestfulService {


    @Autowired
    private BankConsumedOrderDao bankConsumedOrderDao;
    @Autowired
    private BankDepositOrderDao depositOrderDao;

    @Autowired
    private BankAcountDao acountDao;

    @Autowired
    private BankMoneyFlowDao moneyFlowDao;

    @Autowired
    private UserRestfulService userInfoService;

    @Autowired
    private UserCouponDao userCouponDao;


    /**
     * 获取用户消费明细
     *
     * @param userId
     * @return
     */
    @Override
    public List<bankConsumedOrder> getBankConsumedOrderList(long userId) throws Exception {
        return bankConsumedOrderDao.getBankConsumedOrderList(userId, ConsumedStatus.susuccess);
    }

    /**
     * 余额充值
     *
     * @param depositOrder
     */
    public void recharge(bankDepositOrder depositOrder) {

        //先记录充值记录
        depositOrder.setCreateAt(UnixTimeUtils.now());
        depositOrder.setRechargeType(RechargeType.balance.getValue());
        long depositId = depositOrderDao.save(depositOrder);

        //记录现金流水
        moneyFlowDao.save(flowInfo(depositOrder, depositId));

        if (depositId > 0) {

            bankAcount bankAcountCash = acountDao.getAcount(depositOrder.getUserId(), AcountType.cash);

            //充值现金
            if (bankAcountCash != null) {
                acountDao.updateAcount(depositOrder.getUserId(), AcountType.cash, bankAcountCash.getPrice().add(depositOrder.getCash()));

            } else {
                acountDao.save(depositToAcount(depositOrder, AcountType.cash));

            }

            //充值优惠
            bankAcount bankAcountbalance = acountDao.getAcount(depositOrder.getUserId(), AcountType.balance);
            if (bankAcountbalance != null) {
                acountDao.updateAcount(depositOrder.getUserId(), AcountType.balance, bankAcountCash.getPrice().add(depositOrder.getAward()));

            } else {

                acountDao.save(depositToAcount(depositOrder, AcountType.balance));

            }
        }

    }

    /**
     * 押金
     *
     * @param depositOrder
     */
    public void depositRecharge(bankDepositOrder depositOrder) {

        //先记录充值记录
        depositOrder.setCreateAt(UnixTimeUtils.now());
        depositOrder.setRechargeType(RechargeType.deposit.getValue());
        long depositId = depositOrderDao.save(depositOrder);

        //记录现金流水
        moneyFlowDao.save(flowInfo(depositOrder, depositId));

        //修改用户押金状态
        userInfo userInfo = new userInfo();
        userInfo.setId(depositOrder.getUserId());
        userInfo.setSecurityStatus(SecurityStatus.normal.getValue());
        try {
            userInfoService.updateUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取用户充值明细
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<bankDepositOrder> getBankDepositOrderList(long userId) throws Exception {
        return depositOrderDao.getBankDepositOrderList(userId, DepositStatus.susuccess);
    }

    /**
     * 用户优惠券发放
     *
     * @param userCoupon
     * @return
     */
    public long addUserCoupon(userCoupon userCoupon) {
        return userCouponDao.save(userCoupon);
    }

    /**
     * 删除用户的优惠券
     *
     * @param map
     * @return
     */
    public long deleteUserCoupon(Map map) {
        return userCouponDao.deleteUserCoupon(map);
    }

    /**
     * 修改用户优惠券信息
     *
     * @param map
     * @return
     */
    public long updateCoupon(Map map) {
        return userCouponDao.updateCoupon(map);
    }

    /**
     * 获取用户当前可使用的优惠券
     *
     * @param userId
     * @param useAt
     * @return
     */
    public List<userCoupon> getValidCouponList(long userId, int useAt) {
        return userCouponDao.getValidList(userId, useAt);
    }

    /*==================================================*/

    /**
     * 充值不同账户的金额
     *
     * @param order
     * @param acountType
     * @return
     */
    public static bankAcount depositToAcount(bankDepositOrder order, AcountType acountType) {
        bankAcount acount = new bankAcount();
        acount.setUserId(order.getUserId());
        acount.setAcountType(acountType.getValue());
        acount.setServiceType(ServiceType.master.getValue());
        if (acountType.getValue() == 0) acount.setPrice(order.getCash());
        if (acountType.getValue() == 1) acount.setPrice(order.getAward());
        acount.setCreateAt(UnixTimeUtils.now());
        acount.setUpdateAt(0);
        return acount;
    }

    /**
     * 现金流记录
     *
     * @param order
     */
    public static bankMoneyFlow flowInfo(bankDepositOrder order, long depositId) {
        bankMoneyFlow moneyFlow = new bankMoneyFlow();
        moneyFlow.setUserId(order.getUserId());
        moneyFlow.setDealType(DealType.deposit.getValue());
        moneyFlow.setSourceType(order.getPayType());
        moneyFlow.setPayAt(order.getPayAt());
        moneyFlow.setCash(order.getCash());
        moneyFlow.setAward(order.getAward());
        moneyFlow.setDepositId(depositId);
        moneyFlow.setCreateAt(UnixTimeUtils.now());
        return moneyFlow;
    }
    /*==================================================*/

}
