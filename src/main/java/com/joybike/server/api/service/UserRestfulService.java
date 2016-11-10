package com.joybike.server.api.service;

import com.joybike.server.api.model.userInfo;

/**
 * Created by lishaoyong on 16/10/23.
 */
public interface UserRestfulService {

    /**
     * 根据userId获取用户余额
     *
     * @param userId
     * @return
     */
    double getUserAcountMoneyByuserId(long userId) throws Exception;

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    int updateUserInfo(userInfo user) throws Exception;


    /**
     * 根据用户电话号码获取用户信息，判断用户是否存在,如果存在,获取用户信息直接返回，未存在则创建用户信息
     *
     * @param mobile
     * @return
     */
    userInfo getUserInfoByMobile(String mobile) throws Exception;

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    userInfo getUserInfoById(long userId) throws Exception;


}
