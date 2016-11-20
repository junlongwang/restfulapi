package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.*;
import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
import com.joybike.server.api.dao.*;
import com.joybike.server.api.dto.AlipayDto;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.ToHashMap;
import com.joybike.server.api.util.UnixTimeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class PayRestfulServiceImpl implements PayRestfulService {

    private final Logger logger = Logger.getLogger(PayRestfulServiceImpl.class);


    @Autowired
    private BankConsumedOrderDao bankConsumedOrderDao;
    @Autowired
    private BankDepositOrderDao depositOrderDao;

    @Autowired
    private BankAcountDao acountDao;

    @Autowired
    private BankMoneyFlowDao moneyFlowDao;

    @Autowired
    private SubscribeInfoDao subscribeInfoDao;

    @Autowired
    private UserCouponDao userCouponDao;

    @Autowired
    private BankRefundOrderDao bankRefundOrderDao;

    @Autowired
    private VehicleOrderDao vehicleOrderDao;

    @Autowired
    AliPayConstructUrlInter aliPayConstructUrlInter;


    /**
     * 获取用户消费明细
     *
     * @param userId
     * @return
     */
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long recharge(bankDepositOrder depositOrder) throws Exception {
        //先记录充值记录
        depositOrder.setCreateAt(UnixTimeUtils.now());
        depositOrder.setRechargeType(RechargeType.balance.getValue());
        depositOrder.setResidualCash(depositOrder.getCash());
        depositOrder.setResidualAward(depositOrder.getAward());
        depositOrder.setStatus(DepositStatus.initial.getValue());
        depositOrder.setDiscountAt(0);
        long depositId = depositOrderDao.save(depositOrder);

        //记录现金流水
        //userId,order.getPayType(),consumedId,order.getCash(),order.getAward()
        moneyFlowDao.save(flowInfo(depositOrder.getUserId(), depositOrder.getPayType(), depositId, depositOrder.getCash(), depositOrder.getAward(), 0, "", DealType.deposit, 0));
        return depositId;
    }

    /**
     * 押金
     *
     * @param depositOrder
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public long depositRecharge(bankDepositOrder depositOrder) throws Exception {
        //先记录充值记录
        depositOrder.setCreateAt(UnixTimeUtils.now());
        depositOrder.setRechargeType(RechargeType.deposit.getValue());
        long depositId = depositOrderDao.save(depositOrder);

        //记录现金流水
        //        //userId,order.getPayType(),consumedId,order.getCash(),order.getAward()

        moneyFlowDao.save(flowInfo(depositOrder.getUserId(), depositOrder.getPayType(), depositId, depositOrder.getCash(), depositOrder.getAward(), 0, "", DealType.deposit, 0));
        return depositId;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
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

    /**
     * 获取充值ID
     * @param userId
     * @return
     */
    @Override
    public bankDepositOrder getDepositOrderId(Long userId) {
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateDepositOrderById(long id, PayType payType, String payDocumentId, String merchantId, int payAt) throws Exception {

        int updateCount = depositOrderDao.updateDepositOrderById(id, payType, payDocumentId, merchantId, payAt);

        logger.info("余额充值回调:" + payType +"," + "充值信息修改:" + updateCount);
        //充值回调成功的时候修改用户的余额信息
        if (updateCount > 0) {
            bankDepositOrder depositOrder = depositOrderDao.getDepositOrderById(id);
            bankAcount bankAcountCash = acountDao.getAcount(depositOrder.getUserId(), AcountType.cash);

            //充值现金
            if (bankAcountCash != null) {
                acountDao.updateAcount(depositOrder.getUserId(), AcountType.cash, bankAcountCash.getPrice().add(depositOrder.getCash()));
                logger.info("现金充值成功");
            } else {
                acountDao.save(depositToAcount(depositOrder, AcountType.cash));
                logger.info("现金账户创建成功");
            }

            //充值优惠
            bankAcount bankAward = acountDao.getAcount(depositOrder.getUserId(), AcountType.balance);
            if (bankAward != null) {
                acountDao.updateAcount(depositOrder.getUserId(), AcountType.balance, bankAward.getPrice().add(depositOrder.getAward()));
                logger.info("优惠充值成功");
            } else {

                acountDao.save(depositToAcount(depositOrder, AcountType.balance));
                logger.info("优惠账户创建成功");

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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int consume(String orderCode, BigDecimal payPrice, long userId, long consumedDepositId) throws Exception {

        VehicleOrderDto dto =  vehicleOrderDao.getOrderByOrderCode(orderCode);

        if (dto.getStatus() == 15){
            throw new RestfulException(ReturnEnum.NoPay);
        }else{
            double amount = acountDao.getUserAmount(userId);

            bankDepositOrder bankDepositOrder = depositOrderDao.getDepositOrderById(consumedDepositId);

            //可用余额不足,返回支付
            if (BigDecimal.valueOf(amount).compareTo(payPrice) < 0) {
//            throw new RestfulException(ReturnEnum.Pay_Low);
                return -1;
                //可用余额充足,扣费

            } else if (BigDecimal.valueOf(amount).compareTo(payPrice) >= 0) {

                BigDecimal price = BigDecimal.valueOf(0).add(payPrice);

                long consumedId = 0;
                //记录消费信息，如果有金额，先记录消费信息
                if (bankDepositOrder != null) {
                    consumedId = bankConsumedOrderDao.save(bankConsumedOrderInfo(userId, orderCode, payPrice, bankDepositOrder.getAward().add(bankDepositOrder.getCash()), ConsumedStatus.susuccess, 0));

                } else {
                    consumedId = bankConsumedOrderDao.save(bankConsumedOrderInfo(userId, orderCode, payPrice, BigDecimal.valueOf(0), ConsumedStatus.susuccess, 0));
                }

                List<bankDepositOrder> depositList = depositOrderDao.getConsumedDepositOrderList(userId, DepositStatus.susuccess);

                depositList.stream().sorted((p, p2) -> (p.getPayAt().compareTo(p2.getPayAt()))).collect(toList());

                //充值扣款
                for (bankDepositOrder order : depositList) {

                    if (order.getResidualAward().add(order.getResidualCash()).compareTo(payPrice) >= 0) {

                        try {

                            if (order.getResidualAward().compareTo(payPrice) >= 0) {

                                order.setResidualAward(order.getResidualAward().subtract(payPrice));

                                //修改充值
                                depositOrderDao.update(order);
                                //保存流水
                                moneyFlowDao.save(flowInfo(userId, order.getPayType(), order.getId(), BigDecimal.valueOf(0), payPrice, consumedId, orderCode, DealType.consumed, 3));
                                payPrice = BigDecimal.valueOf(0);
                            }

                            if (order.getResidualAward().compareTo(payPrice) < 0) {

                                //修改账户信息
                                BigDecimal cash = payPrice.subtract(order.getResidualAward());

                                order.setResidualCash(order.getResidualCash().subtract(cash));
                                order.setResidualAward(BigDecimal.valueOf(0));
                                //修改充值
                                depositOrderDao.update(order);
                                //保存流水
                                moneyFlowDao.save(flowInfo(userId, order.getPayType(), order.getId(), cash, payPrice.subtract(cash), consumedId, orderCode, DealType.consumed, 3));
                                payPrice = BigDecimal.valueOf(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (order.getResidualAward().add(order.getResidualCash()).compareTo(payPrice) < 0) {

                        payPrice = payPrice.subtract(order.getResidualCash()).subtract(order.getResidualAward());

                        try {

                            order.setResidualCash(BigDecimal.valueOf(0));
                            order.setResidualAward(BigDecimal.valueOf(0));
                            //修改充值
                            depositOrderDao.update(order);
                            //保存流水

                            moneyFlowDao.save(flowInfo(userId, order.getPayType(), order.getId(), order.getResidualCash(), order.getResidualAward(), consumedId, orderCode, DealType.consumed, 3));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                bankAcount cashAmount = acountDao.getAcount(userId, AcountType.cash);
                bankAcount blanceAmount = acountDao.getAcount(userId, AcountType.balance);

                if (blanceAmount.getPrice().compareTo(price) >= 0) {
                    acountDao.updateAcount(userId, AcountType.balance, blanceAmount.getPrice().subtract(price));
                }
                if (blanceAmount.getPrice().compareTo(price) < 0) {
                    BigDecimal cash = price.subtract(blanceAmount.getPrice());
                    acountDao.updateAcount(userId, AcountType.balance, price.subtract(cash));
                    acountDao.updateAcount(userId, AcountType.cash, cashAmount.getPrice().subtract(cash));
                }

                //修改订单支付状态为付款完成
                vehicleOrderDao.updateStatausByCode(orderCode,consumedId);
                subscribeInfoDao.deleteByOrderCode(orderCode);

                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 根据用户ID 与code获取支付订单
     * @param userId
     * @param orderCode
     * @return
     * @throws Exception
     */
    @Override
    public vehicleOrder getNoPayByOrder(long userId, String orderCode) throws Exception {
        return vehicleOrderDao.getNoPayByOrder(userId, orderCode);
    }

    @Override
    public bankDepositOrder getbankDepostiOrderByid(long id) throws Exception {
        return depositOrderDao.getDepositOrderById(id);
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
     * @param
     */
    public static bankMoneyFlow flowInfo(long userId, int payType, long depositId, BigDecimal cash, BigDecimal award, long consumedId, String orderCode, DealType dealType, int status) {

        bankMoneyFlow moneyFlow = new bankMoneyFlow();
        moneyFlow.setUserId(userId);
        moneyFlow.setDealType(dealType.getValue());
        moneyFlow.setSourceType(payType);
        moneyFlow.setPayAt(UnixTimeUtils.now());
        moneyFlow.setCash(cash);
        moneyFlow.setAward(award);
        moneyFlow.setDepositId(depositId);
        moneyFlow.setCreateAt(UnixTimeUtils.now());
        moneyFlow.setConsumedId(consumedId);
        moneyFlow.setSourceOrderCode(orderCode);
        moneyFlow.setStatus(status);
        return moneyFlow;
    }

    public static bankConsumedOrder bankConsumedOrderInfo(long userId, String orderCode, BigDecimal payPrice, BigDecimal depositAmount, ConsumedStatus consumedStatus, long depositId) {

        //保存消费信息
        bankConsumedOrder consumedOrder = new bankConsumedOrder();
        consumedOrder.setUserId(userId);
        consumedOrder.setOrderCode(orderCode);
        consumedOrder.setPayAmount(payPrice);
        consumedOrder.setDepositAmount(depositAmount);
        consumedOrder.setPayAt(UnixTimeUtils.now());
        consumedOrder.setStatus(consumedStatus.getValue());
        consumedOrder.setDepositId(depositId);
        consumedOrder.setCreateAt(UnixTimeUtils.now());
        return consumedOrder;
    }
    /*==================================================*/


    /**
     * 创建退款订单并获取订单id
     *
     * @param bankRefundOrder
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Long creatRefundOrder(bankRefundOrder bankRefundOrder) {
        return bankRefundOrderDao.save(bankRefundOrder);
    }

    /**
     * 退款完毕并更新退款订单为退款成功状态
     *
     * @param Id
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateRefundOrderStatusById(Long Id) {
        return bankRefundOrderDao.updateRefundOrderStatusById(Id);
    }


    /**
     * 组合
     * @param bean
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public String payBeanToAliPay(ThirdPayBean bean,long orderId) throws Exception{
        AlipayDto dto = new AlipayDto();

        long b_Pay_time = (bean.getRecordTime().getTime() - bean.getCreateTime().getTime())/(1000 * 60);

        dto.setOut_trade_no(String.valueOf(orderId));
        dto.setAppenv(bean.getOperIP());
        dto.setBody(bean.getPruductDesc());
        dto.setIt_b_pay(String.valueOf(b_Pay_time));
        dto.setTotal_fee(String.valueOf(bean.getOrderMoney()));
        dto.setSubject(bean.getOrderDesc());

        HashMap<String,String> map = ToHashMap.beanToMap(dto);
        RedirectParam para = aliPayConstructUrlInter.getUrl(map);
        return para.getPara();
    }

    /**
     * 押金充值回调成功更新充值订单信息
     * @param id
     * @param transactionId
     * @param pay_at
     * @param status
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public int updateDepositOrderById_Yajin(long id, String transactionId, int pay_at, int status) throws Exception{
        return depositOrderDao.updateDepositOrderById_Yajin(id,transactionId,pay_at,status);
    }
}
