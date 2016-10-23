package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.ConsumedStatus;
import com.joybike.server.api.dao.BankConsumedOrderDao;
import com.joybike.server.api.model.bankConsumedOrder;
import com.joybike.server.api.service.BankConsumedOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class BankConsumedOrderServiceImpl implements BankConsumedOrderService {

    @Autowired
    BankConsumedOrderDao bankConsumedOrderDao;



    /**
     * 获取用户消费明细
     * @param userId
     * @return
     */
    @Override
    public List<bankConsumedOrder> getBankConsumedOrderList(long userId) throws Exception{
        return bankConsumedOrderDao.getBankConsumedOrderList(userId, ConsumedStatus.susuccess);
    }
}
