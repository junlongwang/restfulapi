package com.joybike.server.api.service;

import com.joybike.server.api.model.bankDepositOrder;

/**
 * Created by lishaoyong on 16/10/19.
 */
public interface BankDepositOrderService {

    /**
     * 充值
     * @param depositOrder
     */
    void recharge(bankDepositOrder depositOrder);

    /**
     * 押金充值
     * @param depositOrder
     */
    void depositRecharge(bankDepositOrder depositOrder);

}
