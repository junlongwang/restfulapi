package com.joybike.server.api.service;

import com.joybike.server.api.model.userInfo;

/**
 * 用户体系服务
 * Created by 58 on 2016/10/18.
 */

public interface UserInfoService {

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUserInfo(userInfo user);
}
