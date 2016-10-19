package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.userInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by 58 on 2016/10/12.
 */
@Repository("userDao")
public class UserInfoDaoImpl extends Reository<userInfo> implements UserInfoDao {

    /**
     * 修改用户数据
     * @param userInfo
     * @return
     */
    public int updateUserInfo(userInfo userInfo) {
        return update(userInfo);
    }

}
