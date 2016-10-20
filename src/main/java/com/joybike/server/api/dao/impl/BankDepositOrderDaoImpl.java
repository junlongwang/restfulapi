package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankDepositOrderDao;
import com.joybike.server.api.model.bankDepositOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankDepositOrderDao")
public class BankDepositOrderDaoImpl extends Reository<bankDepositOrder> implements BankDepositOrderDao {
}
