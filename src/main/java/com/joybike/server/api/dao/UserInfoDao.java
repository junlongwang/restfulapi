package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.userInfo;


/**
 * Created by 58 on 2016/10/12.
 */
public interface UserInfoDao extends IRepository<userInfo> {

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    int updateUserInfo(userInfo userInfo);


}
