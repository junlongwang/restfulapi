package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.DepositStatus;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.bankDepositOrder;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/19.
 */
public interface BankDepositOrderDao extends IRepository<bankDepositOrder> {

    /**
     * 获取用户充值记录
     *
     * @param userId
     * @param depositStatus
     * @return
     */
    List<bankDepositOrder> getBankDepositOrderList(long userId, DepositStatus depositStatus) throws Exception;

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
    int updateDepositOrderById(long id, PayType payType, String payDocumentId, String merchantId, int payAt);


    /**
     * 根据充值ID获取充值记录
     *
     * @param id
     * @return
     */
    bankDepositOrder getDepositOrderById(long id) throws Exception;

    /**
     * 获取可消费的充值
     *
     * @param userId
     * @param depositStatus
     * @return
     */
    List<bankDepositOrder> getConsumedDepositOrderList(long userId, DepositStatus depositStatus) throws Exception;



    /**
     * 获取用户押金充值成功订单id和支付通路id
     * @param userId
     * @return
     */
    bankDepositOrder getDepositOrder(Long userId);

    /**
     * 押金充值回调成功更新充值订单信息
     * @param id
     * @param transactionId
     * @param pay_at
     * @param status
     * @return
     * @throws Exception
     */
    public int updateDepositOrderById_Yajin(long id, String transactionId, int pay_at, int status);
}
