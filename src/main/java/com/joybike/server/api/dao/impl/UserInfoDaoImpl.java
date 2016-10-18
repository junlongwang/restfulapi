package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.po.userInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by 58 on 2016/10/12.
 */
@Repository("userDao")
public class UserInfoDaoImpl extends Reository<userInfo> implements UserInfoDao {

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    public long insertUserInfo(userInfo userInfo) {
        return save(userInfo);
    }

    /**
     * 修改用户数据
     * @param userInfo
     * @return
     */
    public int updateUserInfo(userInfo userInfo) {
        return update(userInfo);
    }

}
