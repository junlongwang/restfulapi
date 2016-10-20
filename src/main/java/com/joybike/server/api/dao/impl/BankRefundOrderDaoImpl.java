package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankRefundOrderDao;
import com.joybike.server.api.model.bankRefundOrder;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankRefundOrderDao")
public class BankRefundOrderDaoImpl extends Reository<bankRefundOrder> implements BankRefundOrderDao {
}
