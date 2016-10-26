package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.*;
import com.joybike.server.api.dao.*;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

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

    @Autowired
    private BankRefundOrderDao bankRefundOrderDao;




    /**
     * 获取用户消费明细
     *
     * @param userId
     * @return
     */
    @Transactional
    @Override
    public List<bankConsumedOrder> getBankConsumedOrderList(long userId) throws Exception {
        try {
            return bankConsumedOrderDao.getBankConsumedOrderList(userId, ConsumedStatus.susuccess);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.SERVICE_ERROR);
        }

    }

    /**
     * 余额充值
     *
     * @param depositOrder
     */
    @Transactional
    @Override
    public void recharge(bankDepositOrder depositOrder) throws Exception {
        //先记录充值记录
        depositOrder.setCreateAt(UnixTimeUtils.now());
        depositOrder.setRechargeType(RechargeType.balance.getValue());
        depositOrder.setResidualCash(depositOrder.getCash());
        depositOrder.setResidualAward(depositOrder.getAward());
        depositOrder.setStatus(DepositStatus.initial.getValue());
        depositOrder.setDiscountAt(0);
        long depositId = depositOrderDao.save(depositOrder);

        //记录现金流水
        moneyFlowDao.save(flowInfo(depositOrder, depositId));

    }

    /**
     * 押金
     *
     * @param depositOrder
     */
    @Transactional
    @Override
    public void depositRecharge(bankDepositOrder depositOrder) throws Exception {
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
        userInfoService.updateUserInfo(userInfo);
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
    @Transactional
    @Override
    public long addUserCoupon(userCoupon userCoupon) throws Exception {
        return userCouponDao.save(userCoupon);
    }

    /**
     * 删除用户的优惠券
     *
     * @param map
     * @return
     */
    @Transactional
    @Override
    public long deleteUserCoupon(Map map) throws Exception {

        return userCouponDao.deleteUserCoupon(map);
    }

    /**
     * 修改用户优惠券信息
     *
     * @param map
     * @return
     */
    @Transactional
    @Override
    public long updateCoupon(Map map) throws Exception {
        return userCouponDao.updateCoupon(map);
    }

    /**
     * 获取用户当前可使用的优惠券
     *
     * @param userId
     * @param useAt
     * @return
     */
    @Override
    public List<userCoupon> getValidCouponList(long userId, int useAt) throws Exception {
        return userCouponDao.getValidList(userId, useAt);
    }

    @Override
    public bankDepositOrder getDepositOrderId(Long userId){
        return depositOrderDao.getDepositOrder(userId);
    }

    /**
     * 充值成功回调
     *
     * @param id
     * @param payType
     * @param payDocumentId
     * @param merchantId
     * @param payAt
     * @return
     */
    @Transactional
    @Override
    public int updateDepositOrderById(long id, PayType payType, String payDocumentId, String merchantId, int payAt) throws Exception {
        int updateCount = depositOrderDao.updateDepositOrderById(id, payType, payDocumentId, merchantId, payAt);
        //充值回调成功的时候修改用户的余额信息
        if (updateCount > 0) {
            bankDepositOrder depositOrder = depositOrderDao.getDepositOrderById(id);
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
        return updateCount;
    }

    /**
     * 支付消费
     *
     * @param orderCode
     * @param payPrice
     * @param userId
     * @return
     */
    @Override
    public int consume(String orderCode, BigDecimal payPrice, long userId) throws Exception{

        double amount = acountDao.getUserAmount(userId);

        if (BigDecimal.valueOf(amount).compareTo(payPrice) < 0){
            throw new RestfulException(ReturnEnum.Pay_Low);
        }else{
            List<bankDepositOrder> depositList = depositOrderDao.getBankDepositOrderList(userId, DepositStatus.susuccess);

            depositList.stream().sorted((p, p2) -> (p.getPayAt() - p2.getPayAt())).collect(toList());
            depositList.forEach(new Consumer<bankDepositOrder>() {
                @Override
                public void accept(bankDepositOrder order) {
                    if (order.getAward().add(order.getCash()).compareTo(payPrice) == 0){
                        try {
                            //修改余额
                            acountDao.updateAcount(userId, AcountType.cash, order.getCash());
                            acountDao.updateAcount(userId, AcountType.balance, order.getAward());
                            order.setResidualCash(BigDecimal.valueOf(0));
                            order.setResidualAward(BigDecimal.valueOf(0));
                            //修改充值
                            depositOrderDao.update(order);
                            //保存消费信息
                            bankConsumedOrder consumedOrder = new bankConsumedOrder();
                            consumedOrder.setUserId(userId);
                            consumedOrder.setOrderCode(orderCode);
                            consumedOrder.setPayAmount(payPrice);
                            consumedOrder.setDepositAmount(BigDecimal.valueOf(0));
                            consumedOrder.setPayAt(UnixTimeUtils.now());
                            consumedOrder.setStatus(ConsumedStatus.susuccess.getValue());
                            consumedOrder.setDepositId(order.getId());
                            consumedOrder.setCreateAt(UnixTimeUtils.now());
                            long consumedId = bankConsumedOrderDao.save(consumedOrder);
                            //保存流水
                            bankMoneyFlow flow = new bankMoneyFlow();
                            flow.setUserId(userId);
                            flow.setDealType(DealType.consumed.getValue());
                            flow.setSourceType(order.getPayType());
                            flow.setDepositId(order.getId());
                            flow.setSourceOrderCode(orderCode);
                            flow.setPayAt(UnixTimeUtils.now());
                            flow.setConsumedId(consumedId);
                            flow.setCash(order.getCash());
                            flow.setAward(order.getAward());
                            flow.setRefundAt(0);
                            flow.setCreateAt(UnixTimeUtils.now());
                            moneyFlowDao.save(flow);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if (order.getAward().add(order.getCash()).compareTo(payPrice) == 0){
                        try {
                            //修改余额
                            if (order.getAward().compareTo(payPrice) > 0){
                                // todoz这里了
//                                acountDao.updateAcount(userId, AcountType.cash, order.getCash().subtract(payPrice));
                                acountDao.updateAcount(userId, AcountType.balance, order.getAward().subtract(payPrice));
                                order.setResidualCash(BigDecimal.valueOf(0));
                                order.setResidualAward(BigDecimal.valueOf(0));
                                //修改充值
                                depositOrderDao.update(order);
                                //保存消费信息
                                bankConsumedOrder consumedOrder = new bankConsumedOrder();
                                consumedOrder.setUserId(userId);
                                consumedOrder.setOrderCode(orderCode);
                                consumedOrder.setPayAmount(payPrice);
                                consumedOrder.setDepositAmount(BigDecimal.valueOf(0));
                                consumedOrder.setPayAt(UnixTimeUtils.now());
                                consumedOrder.setStatus(ConsumedStatus.susuccess.getValue());
                                consumedOrder.setDepositId(order.getId());
                                consumedOrder.setCreateAt(UnixTimeUtils.now());
                                long consumedId = bankConsumedOrderDao.save(consumedOrder);
                                //保存流水
                                bankMoneyFlow flow = new bankMoneyFlow();
                                flow.setUserId(userId);
                                flow.setDealType(DealType.consumed.getValue());
                                flow.setSourceType(order.getPayType());
                                flow.setDepositId(order.getId());
                                flow.setSourceOrderCode(orderCode);
                                flow.setPayAt(UnixTimeUtils.now());
                                flow.setConsumedId(consumedId);
                                flow.setCash(order.getCash());
                                flow.setAward(order.getAward());
                                flow.setRefundAt(0);
                                flow.setCreateAt(UnixTimeUtils.now());
                                moneyFlowDao.save(flow);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


            List<bankConsumedOrder> consumedList = bankConsumedOrderDao.getBankConsumedOrderList(userId, ConsumedStatus.susuccess);

        }
        return 0;
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


    /**
     * 创建退款订单并获取订单id
     * @param bankRefundOrder
     * @return
     */
    public Long creatRefundOrder(bankRefundOrder bankRefundOrder){
        return bankRefundOrderDao.save(bankRefundOrder);
    }

    /**
     * 退款完毕并更新退款订单为退款成功状态
     * @param Id
     * @return
     */
    public int updateRefundOrderStatusById(Long Id){
        return bankRefundOrderDao.updateRefundOrderStatusById(Id);
    }
}
