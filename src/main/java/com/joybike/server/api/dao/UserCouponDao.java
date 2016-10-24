package com.joybike.server.api.dao;




import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.userCoupon;

import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/18.
 */
public interface UserCouponDao extends IRepository<userCoupon> {


    /**
     * 删除用户的优惠券
     * @param map
     * @return
     */
    long deleteUserCoupon(Map map) throws Exception;

    /**
     * 修改用户优惠券信息
     * @param map
     * @return
     */
    long updateCoupon(Map map) throws Exception;

    /**
     * 获取用户当前可使用的优惠券
     * @param userId
     * @param useAt
     * @return
     */
    List<userCoupon> getValidList(long userId,int useAt) throws Exception;

    /**
     * 获取用户有效的优惠券数量
     * @param userId
     * @return
     */
    int getValidCount(long userId ,int expireAt) throws Exception;
}
