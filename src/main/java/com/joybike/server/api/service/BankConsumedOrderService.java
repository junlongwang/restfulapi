package com.joybike.server.api.service;

import com.joybike.server.api.model.bankConsumedOrder;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/20.
 */
public interface BankConsumedOrderService {


    /**
     * 获取用户消费明细
     * @param userId
     * @return
     */
    List<bankConsumedOrder> getBankConsumedOrderList(long userId) throws Exception;
}
