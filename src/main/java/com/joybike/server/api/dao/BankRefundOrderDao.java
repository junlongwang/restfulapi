package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.bankRefundOrder;

/**
 * Created by lishaoyong on 16/10/19.
 */
public interface BankRefundOrderDao extends IRepository<bankRefundOrder> {


    /**
     * 退款完毕并更新退款订单为退款成功状态
     * @param id
     * @return
     */
    public int updateRefundOrderStatusById(Long id);

}
