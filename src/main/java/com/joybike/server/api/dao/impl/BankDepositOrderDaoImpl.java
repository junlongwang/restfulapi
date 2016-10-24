package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.DepositStatus;
import com.joybike.server.api.Enum.ErrorEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankDepositOrderDao;
import com.joybike.server.api.model.bankConsumedOrder;
import com.joybike.server.api.model.bankDepositOrder;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankDepositOrderDao")
public class BankDepositOrderDaoImpl extends Reository<bankDepositOrder> implements BankDepositOrderDao {

    /**
     * 获取用户充值记录
     *
     * @param userId
     * @return
     */
    final String getBankDepositOrderListSql = "select * from bankDepositOrder where userId = ? and status = ? ";

    @Override
    public List<bankDepositOrder> getBankDepositOrderList(long userId,DepositStatus depositStatus)  throws Exception{
        Object[] object = new Object[]{userId,depositStatus.getValue()};
        try {
            return this.jdbcTemplate.getJdbcOperations().query(getBankDepositOrderListSql, object, new BeanPropertyRowMapper(bankDepositOrder.class));
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }
    }
}
