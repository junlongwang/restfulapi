package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.OrderItemDao;
import com.joybike.server.api.model.orderItem;
import org.springframework.stereotype.Repository;

/**
 * Created by lishaoyong on 16/10/21.
 */
@Repository("OrderItemDao")
public class OrderItemDaoImpl extends Reository<orderItem> implements OrderItemDao {
}
