package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.bankMoneyFlow;

/**
 * Created by lishaoyong on 16/10/19.
 */
public interface BankMoneyFlowDao extends IRepository<bankMoneyFlow> {

    /**
     * 修改流水信息
     *
     * @param depositId
     * @return
     */
    int updateBakMoneyFlow(long depositId);
}
