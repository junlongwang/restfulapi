package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.po.userInfo;

/**
 * Created by 58 on 2016/10/12.
 */
public interface UserInfoDao extends IRepository<userInfo> {

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    long insertUserInfo(userInfo userInfo);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    int updateUserInfo(userInfo userInfo);


}
