package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankConsumedOrderDao;
import com.joybike.server.api.model.bankConsumedOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankConsumedOrderDao")
public class BankConsumedOrderDaoImpl extends Reository<bankConsumedOrder> implements BankConsumedOrderDao {
}
