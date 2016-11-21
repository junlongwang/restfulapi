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

    final String getDepositOrder = "select * from bankDepositOrder where userId = :userId and status = 2 and rechargeType = 1";

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

    /**
     * 根据充值ID获取充值记录
     *
     * @param id
     * @return
     */
    final String getDepositOrderByIdSql = "select * from bankDepositOrder where id = :id";

    @Override
    public bankDepositOrder getDepositOrderById(long id) {
        Map map = new HashMap();
        map.put("id", id);
        try {
            try {
                return (bankDepositOrder) this.jdbcTemplate.queryForObject(getDepositOrderByIdSql, map, new BeanPropertyRowMapper(bankDepositOrder.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }


    /**
     * 获取用户充值记录
     *
     * @param userId
     * @return
     */
    final String getConsumedDepositOrderList = "select * from bankDepositOrder where userId = ? and status = ?  and ((residualCash + residualAward)>0)";

    @Override
    public List<bankDepositOrder> getConsumedDepositOrderList(long userId, DepositStatus depositStatus) throws Exception {
        Object[] object = new Object[]{userId, depositStatus.getValue()};
        try {
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getConsumedDepositOrderList, object, new BeanPropertyRowMapper(bankDepositOrder.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    @Override
    public bankDepositOrder getDepositOrder(Long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        try {
            try {
                return (bankDepositOrder) this.jdbcTemplate.queryForObject(getDepositOrder, map, new BeanPropertyRowMapper(bankDepositOrder.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 押金充值回调成功更新充值订单信息
     * @param id
     * @param transactionId
     * @param pay_at
     * @param status
     * @return
     * @throws Exception
     */
    /**
     * 押金充值成功时根据id更新充值订单状态
     */
    final String UpdateDepositOrderByIdSql_Yajin = "update bankdepositorder set status =:status,payDocumentId =:payDocumentId,payAt =:payAt where id =:id";
    final String updateDepositOrderStatusToRefundById = "update bankdepositorder set status =3 where id =:id";


    @Override
    public int updateDepositOrderById_Yajin(long id, String transactionId, int pay_at, int status){
        try {
            Map map = new HashMap();
            map.put("id", id);
            map.put("status", status);
            map.put("payDocumentId", transactionId);
            map.put("payAt", pay_at);
            return execSQL(UpdateDepositOrderByIdSql_Yajin, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
    @Override
    public  int updateDepositOrderStatusToRefundById(long id){
        try {
            Map map = new HashMap();
            map.put("id", id);
            return execSQL(updateDepositOrderStatusToRefundById, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
