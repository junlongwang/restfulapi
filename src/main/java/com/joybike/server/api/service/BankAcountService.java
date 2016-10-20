package com.joybike.server.api.service;


/**
 * Created by lishaoyong on 16/10/20.
 */
public interface BankAcountService {


    /**
     * 根据userId获取用户余额
     * @param userId
     * @return
     */
    double getUserAcountMoneyByuserId(long userId);
}
