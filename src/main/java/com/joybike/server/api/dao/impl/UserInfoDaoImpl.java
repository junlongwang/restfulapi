package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.userInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 58 on 2016/10/12.
 */
@Repository("userDao")
public class UserInfoDaoImpl extends Reository<userInfo> implements UserInfoDao {

    /**
     * 修改用户数据
     *
     * @param user
     * @return
     */
    public int updateUserInfo(userInfo user) {
        return update(user);
    }

    /**
     * 获取用户基本信息
     *
     * @param userId
     * @return
     */
    final String getUserInfoSql = "select * from userInfo where id = :userId";

    final String getUserInfoByMobileSql = "select * from userInfo where iphone = :mobile";

    public userInfo getUserInfo(long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        return (userInfo) this.jdbcTemplate.queryForObject(getUserInfoSql, map, new BeanPropertyRowMapper(userInfo.class));
    }

    @Override
    public userInfo getUserInfoByMobile(String mobile) {
        Map map = new HashMap();
        map.put("mobile", mobile);
        return (userInfo) this.jdbcTemplate.queryForObject(getUserInfoByMobileSql, map, new BeanPropertyRowMapper(userInfo.class));
    }

}
