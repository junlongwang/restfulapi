package com.joybike.server.api.service;

import com.joybike.server.api.model.userCoupon;

import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
public interface UserCouponService {

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

    /**
     * 获取用户当前可使用的优惠券
     * @param userId
     * @param useAt
     * @return
     */
    List<userCoupon> getValidList(long userId,int useAt);
}
