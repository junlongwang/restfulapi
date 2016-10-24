package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ConsumedStatus;
import com.joybike.server.api.Enum.ErrorEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankConsumedOrderDao;
import com.joybike.server.api.model.bankAcount;
import com.joybike.server.api.model.bankConsumedOrder;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankConsumedOrderDao")
public class BankConsumedOrderDaoImpl extends Reository<bankConsumedOrder> implements BankConsumedOrderDao {

    /**
     * 获取用户消费明细
     *
     * @param userId
     * @param status
     * @return
     */
    final String getBankConsumedOrderListSql = "select * from bankConsumedOrder where userId = ? and status = ?";

    @Override
    public List<bankConsumedOrder> getBankConsumedOrderList(long userId, ConsumedStatus consumedStatus) throws Exception {

        try {
            Object[] object = new Object[]{userId, consumedStatus.getValue()};
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getBankConsumedOrderListSql, object, new BeanPropertyRowMapper(bankConsumedOrder.class));
            }catch (Exception e){
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }
    }
}
