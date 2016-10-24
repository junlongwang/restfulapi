package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.AcountType;
import com.joybike.server.api.Enum.ErrorEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankAcountDao;
import com.joybike.server.api.model.bankAcount;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
@Repository("BankAcountDao")
public class BankAcountDaoImpl extends Reository<bankAcount> implements BankAcountDao {


    /**
     * 修改账户金额
     *
     * @param userId
     * @param acountType
     * @param price
     * @param updateAt
     * @return
     */
    final String updateAcountSql = "update bankAcount set price = :price ,updateAt = :updateAt where userId = :userId and acountType = :acountType";

    @Override
    public int updateAcount(long userId, AcountType acountType, BigDecimal price) throws Exception {

        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("acountType", acountType.getValue());
            map.put("price", price);
            map.put("updateAt", UnixTimeUtils.now());

            return execSQL(updateAcountSql, map);
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }

    }

    /**
     * 获取用户某个账户余额
     *
     * @param userId
     * @param acountType
     * @return
     */
    final String acountSql = "select * from bankAcount where userId = ? and acountType = ?";

    @Override
    public bankAcount getAcount(long userId, AcountType acountType) throws Exception {

        try {
            Map map = new HashMap();
            map.put("userId", userId);
            map.put("acountType", acountType.getValue());

            return (bankAcount) this.jdbcTemplate.queryForObject(acountSql, map, new BeanPropertyRowMapper(bankAcount.class));
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }
    }


    /**
     * 根据用户ID获取用户总余额
     *
     * @param userId
     * @return
     */
    final String userAmountSql = "select sum(price) price from bankAcount where userId = :userId group by userId";

    @Override
    public double getUserAmount(long userId) throws Exception {

        try {
            Map map = new HashMap();
            map.put("userId", userId);

            return getCount(userAmountSql, map);
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.DATABASE_ERROR);
        }

    }
}
