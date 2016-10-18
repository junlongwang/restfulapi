package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserCouponDao;
import com.joybike.server.api.po.userCoupon;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
@Repository("UserCouponDao")
public class UserCouponDaoImpl extends Reository<userCoupon> implements UserCouponDao {

    /**
     * 用户优惠券发放
     * @param userCoupon
     * @return
     */
    public long insertUserCoupon(userCoupon userCoupon) {
        return save(userCoupon);
    }

    /**
     * 删除用户的优惠券
     * @param map
     * @return
     */
    final String deleteUserCouponSql = "delete from userCoupon where userId = ? and couponId = ?";
    public long deleteUserCoupon(Map map) {
        return execSQL(deleteUserCouponSql,map);
    }

    /**
     * 修改用户优惠券信息
     * @param map
     * @return
     */
    final String updateCouponSql = "update table userCoupon set status = ? where userId = ? and couponId = ? ";
    public long updateCoupon(Map map) {
        return execSQL(updateCouponSql,map);
    }


}
