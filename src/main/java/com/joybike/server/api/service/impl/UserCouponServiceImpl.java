package com.joybike.server.api.service.impl;

import com.joybike.server.api.dao.UserCouponDao;
import com.joybike.server.api.model.userCoupon;
import com.joybike.server.api.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    UserCouponDao userCouponDao;

    /**
     * 用户优惠券发放
     * @param userCoupon
     * @return
     */
    public long insertUserCoupon(userCoupon userCoupon) {
        return userCouponDao.save(userCoupon);
    }
    /**
     * 删除用户的优惠券
     * @param map
     * @return
     */
    public long deleteUserCoupon(Map map) {
        return userCouponDao.deleteUserCoupon(map);
    }
    /**
     * 修改用户优惠券信息
     * @param map
     * @return
     */
    public long updateCoupon(Map map) {
        return userCouponDao.updateCoupon(map);
    }
    /**
     * 获取用户当前可使用的优惠券
     * @param userId
     * @param useAt
     * @return
     */
    public List<userCoupon> getValidList(long userId, int useAt) {
        return userCouponDao.getValidList(userId , useAt);
    }
}
