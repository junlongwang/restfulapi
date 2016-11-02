package com.joybike.server.api.service;

import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.dto.AlipayDto;
import com.joybike.server.api.model.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/23.
 * 支付
 */
public interface PayRestfulService {


    /**
     * 获取用户消费明细
     *
     * @param userId
     * @return
     */
    List<bankConsumedOrder> getBankConsumedOrderList(long userId) throws Exception;


    /**
     * 充值
     *
     * @param depositOrder
     */
    void recharge(bankDepositOrder depositOrder) throws Exception;

    /**
     * 押金充值
     *
     * @param depositOrder
     */
    long depositRecharge(bankDepositOrder depositOrder) throws Exception;


    /**
     * 获取用户充值明细
     *
     * @param userId
     * @return
     * @throws Exception
     */
    List<bankDepositOrder> getBankDepositOrderList(long userId) throws Exception;


    /**
     * 用户优惠券发放
     *
     * @param userCoupon
     * @return
     */
    long addUserCoupon(userCoupon userCoupon) throws Exception;

    /**
     * 删除用户的优惠券
     *
     * @param map
     * @return
     */
    long deleteUserCoupon(Map map) throws Exception;

    /**
     * 修改用户优惠券信息
     *
     * @param map
     * @return
     */
    long updateCoupon(Map map) throws Exception;

    /**
     * 获取用户当前可使用的优惠券
     *
     * @param userId
     * @param useAt
     * @return
     */
    List<userCoupon> getValidCouponList(long userId, int useAt) throws Exception;


    /**
     * 余额充值回调成功充值成功回调
     *
     * @param id
     * @param payType
     * @param payDocumentId
     * @param merchantId
     * @param payAt
     * @return
     */
    int updateDepositOrderById(long id, PayType payType, String payDocumentId, String merchantId, int payAt) throws Exception;

    /**
     * 押金充值回调成功更新充值订单信息
     * @param id
     * @param transactionId
     * @param pay_at
     * @param status
     * @return
     * @throws Exception
     */
    int updateDepositOrderById_Yajin(long id, String transactionId, int pay_at, int status) throws Exception;
    /**
     * 获取用户充值订单ID（有且只有唯一一条支付成功的充值订单ID）
     * @param userid
     * @return
     */
    bankDepositOrder getDepositOrderId(Long userid);


    /**
     * 创建退款订单并获取订单id
     * @param bankRefundOrder
     * @return
     */
    Long creatRefundOrder(bankRefundOrder bankRefundOrder);

    /**
     * 退款完毕并更新退款订单为退款成功状态
     * @param id
     * @return
     */
    int updateRefundOrderStatusById(Long id);

     /* 支付消费
     *
     * @param orderCode
     * @param payPrice
     * @param userId
     * @return
     */
    int consume(String orderCode, BigDecimal payPrice, long userId,long consumedDepositId) throws Exception;


    /**
     * 根据用户ID获取未完成订单
     */
    vehicleOrder getNoPayByOrder(long userId,String orderCode) throws Exception;

    /**
     * 根据订单ID获取订单信息
     * @param id
     * @return
     * @throws Exception
     */
    bankDepositOrder getbankDepostiOrderByid(long id) throws Exception;



    String payBeanToAliPay(ThirdPayBean bean,long orderId) throws Exception;

}
