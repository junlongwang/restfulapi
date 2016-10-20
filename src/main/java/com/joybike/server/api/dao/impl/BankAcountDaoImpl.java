package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.AcountType;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankAcountDao;
import com.joybike.server.api.model.bankAcount;
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

    public int updateAcount(long userId, AcountType acountType, BigDecimal price) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("acountType", acountType.getValue());
        map.put("price", price);
        map.put("updateAt", UnixTimeUtils.now());
        return execSQL(updateAcountSql, map);
    }

    /**
     * 获取用户账户余额
     *
     * @param userId
     * @param acountType
     * @return
     */
    final String getAcountSql = "select * from bankAcount where userId = ? and acountType = ?";

    public bankAcount getAcount(long userId, AcountType acountType) {
        Object[] object = new Object[]{userId, acountType.getValue()};
        List<bankAcount> list = this.jdbcTemplate.getJdbcOperations().query(getAcountSql, object, new BeanPropertyRowMapper(bankAcount.class));
        if (list.size() > 0) return list.get(0);
        else return null;
    }
}
