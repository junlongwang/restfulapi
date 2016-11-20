package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankMoneyFlowDao;
import com.joybike.server.api.model.bankMoneyFlow;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankMoneyFlowDao")
public class BankMoneyFlowDaoImpl extends Reository<bankMoneyFlow> implements BankMoneyFlowDao {

    /**
     * 修改流水信息
     *
     * @param depositId
     * @return
     */
    final String updateBakMoneyFlowSql = "update bankmoneyflow set status = :status  where depositId = :depositId";

    @Override
    public int updateBakMoneyFlow(long depositId) {
        try {
            Map map = new HashMap();
            map.put("status", 3);
            map.put("depositId", depositId);
            return execSQL(updateBakMoneyFlowSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
