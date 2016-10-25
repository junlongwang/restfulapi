package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.DepositStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankDepositOrderDao;
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
    public List<bankDepositOrder> getBankDepositOrderList(long userId, DepositStatus depositStatus) throws Exception {
        Object[] object = new Object[]{userId, depositStatus.getValue()};
        try {
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getBankDepositOrderListSql, object, new BeanPropertyRowMapper(bankDepositOrder.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 充值成功回调
     *
     * @param id
     * @param payType
     * @param payDocumentId
     * @param merchantId
     * @param payAt
     * @return
     */
    final String updateDepositOrderByIdSql = "update bankDepositOrder set status = 2 ,payType = :payType , payDocumentId = :payDocumentId ,merchantId = :merchantId, payAt = :payAt where id = :id";

    @Override
    public int updateDepositOrderById(long id, PayType payType, String payDocumentId, String merchantId, int payAt) {

        try {
            Map map = new HashMap();
            map.put("id", id);
            map.put("payType", payType.getValue());
            map.put("payDocumentId", payDocumentId);
            map.put("merchantId", merchantId);
            map.put("payAt", payAt);
            return execSQL(updateDepositOrderByIdSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
