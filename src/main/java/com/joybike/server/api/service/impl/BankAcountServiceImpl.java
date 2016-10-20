package com.joybike.server.api.service.impl;

import com.joybike.server.api.dao.BankAcountDao;
import com.joybike.server.api.service.BankAcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by lishaoyong on 16/10/20.
 */
@Service
public class BankAcountServiceImpl implements BankAcountService {

    @Autowired
    BankAcountDao acountDao;


    @Override
    public double getUserAcountMoneyByuserId(long userId) {

        return acountDao.getUserAmount(userId);
    }
}
