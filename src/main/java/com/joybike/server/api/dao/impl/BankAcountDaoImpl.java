package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankAcountDao;
import com.joybike.server.api.model.bankAcount;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/18.
 */
@Repository("BankAcountDao")
public class BankAcountDaoImpl extends Reository<bankAcount> implements BankAcountDao {

    /**
     * 添加用户账户信息
     * @param bankAcount
     * @return
     */
    public long insertBankAcount(bankAcount bankAcount) {
        return save(bankAcount);
    }
}
