package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserCouponDao;
import com.joybike.server.api.model.userCoupon;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
@Repository("UserCouponDao")
public class UserCouponDaoImpl extends Reository<userCoupon> implements UserCouponDao {

    /**
     * 删除用户的优惠券
     *
     * @param map
     * @return
     */
    final String deleteUserCouponSql = "delete from userCoupon where userId = :userId and couponId = :couponId";

    @Override
    public long deleteUserCoupon(Map map) {
        return execSQL(deleteUserCouponSql, map);
    }

    /**
     * 修改用户优惠券信息
     *
     * @param map
     * @return
     */
    final String updateCouponSql = "update userCoupon set status = :status where userId = :userId and couponId = :couponId ";

    @Override
    public long updateCoupon(Map map) {
        return execSQL(updateCouponSql, map);
    }

    /**
     * 获取用户当前可使用的优惠券
     *
     * @param userId
     * @return
     */
    final String validCouponSql = "select * from userCoupon where userId = ? and expireAt >= ?";

    @Override
    public List<userCoupon> getValidList(long userId, int useAt) {
        Object[] object = new Object[]{userId, useAt};
        List<userCoupon> list = this.jdbcTemplate.getJdbcOperations().query(validCouponSql, object, new BeanPropertyRowMapper(userCoupon.class));
        return list;
    }

    /**
     * 获取用户有效的优惠券数量
     *
     * @param userId
     * @return
     */
    final String validCountSql = "select count(*) from userCoupon where userId = :userId and expireAt >= :expireAt and status = 0";

    @Override
    public int getValidCount(long userId, int expireAt) {
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("expireAt", expireAt);
        return getCount(validCountSql, map);
    }


}
