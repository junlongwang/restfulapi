package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.po.userCoupon;

import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
public interface UserCouponDao extends IRepository<userCoupon> {


    /**
     * 用户优惠券发放
     * @param userCoupon
     * @return
     */
    long insertUserCoupon(userCoupon userCoupon);

    /**
     * 删除用户的优惠券
     * @param map
     * @return
     */
    long deleteUserCoupon(Map map);

    /**
     * 修改用户优惠券信息
     * @param map
     * @return
     */
    long updateCoupon(Map map);

}
