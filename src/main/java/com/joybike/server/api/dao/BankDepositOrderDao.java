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
    bankDepositOrder getDepositOrderById(long id);

}
