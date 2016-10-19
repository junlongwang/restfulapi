package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.userInfo;



/**
 * Created by 58 on 2016/10/12.
 */
public interface UserInfoDao extends IRepository<userInfo> {

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUserInfo(userInfo user);

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    userInfo getUserInfo(long userId);

    /**
     * 根据用户电话号码获取用户信息
     * @param mobile
     * @return
     */
    userInfo getUserInfoByMobile(String mobile);

}
