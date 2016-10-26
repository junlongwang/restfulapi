package com.joybike.server.api.service;

import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.model.bankConsumedOrder;
import com.joybike.server.api.model.bankDepositOrder;
import com.joybike.server.api.model.userCoupon;

import java.math.BigDecimal;
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
    void depositRecharge(bankDepositOrder depositOrder) throws Exception;


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
     * 充值成功回调
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
     * 支付消费
     *
     * @param orderCode
     * @param payPrice
     * @param userId
     * @return
     */
    int consume(String orderCode, BigDecimal payPrice, long userId) throws Exception;
}
