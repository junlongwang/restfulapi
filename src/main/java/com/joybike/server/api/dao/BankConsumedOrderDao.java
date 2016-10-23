package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.ConsumedStatus;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.bankConsumedOrder;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/19.
 */
public interface BankConsumedOrderDao extends IRepository<bankConsumedOrder> {


    /**
     * 获取用户消费明细
     * @param userId
     * @return
     */
    List<bankConsumedOrder> getBankConsumedOrderList(long userId,ConsumedStatus consumedStatus);
}
