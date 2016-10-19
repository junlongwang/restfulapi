package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.bankAcount;


/**
 * Created by lishaoyong on 16/10/18.
 */
public interface BankAcountDao extends IRepository<bankAcount> {

    /**
     * 添加用户账户信息
     * @param bankAcount
     * @return
     */
    long insertBankAcount(bankAcount bankAcount);

}
