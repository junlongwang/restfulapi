package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankMoneyFlowDao;
import com.joybike.server.api.model.bankMoneyFlow;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankMoneyFlowDao")
public class BankMoneyFlowDaoImpl extends Reository<bankMoneyFlow> implements BankMoneyFlowDao {
}
