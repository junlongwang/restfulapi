package com.joybike.server.api.service;

import com.joybike.server.api.model.userInfo;

/**
 * 用户体系服务
 * Created by 58 on 2016/10/18.
 */

public interface UserInfoService {

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    int updateUserInfo(userInfo user);

    /**
     * 判断用户是否存在,如果存在,获取用户信息直接返回，未存在则创建用户信息
     *
     * @return
     */
    userInfo getUserInfo(String phone);
}
