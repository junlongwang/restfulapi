package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.userCoupon;
import com.joybike.server.api.model.userInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 58 on 2016/10/12.
 */
@Repository("userDao")
public class UserInfoDaoImpl extends Reository<userInfo> implements UserInfoDao {


    /**
     * 获取用户基本信息
     *
     * @param userId
     * @return
     */
    final String userInfoSql = "select * from userInfo where id = :userId";

    @Override
    public userInfo getUserInfo(long userId)  throws Exception{
        Map map = new HashMap();
        map.put("userId", userId);
        try {
            return (userInfo) this.jdbcTemplate.queryForObject(userInfoSql, map, new BeanPropertyRowMapper(userInfo.class));
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 根据用户手机号码获取用户信息
     *
     * @param phone
     * @return
     */
    final String phoneSql = "select * from userInfo where iphone = ?";

    @Override
    public userInfo getInfoByPhone(String phone)  throws Exception{
        Map map = new HashMap();
        map.put("mobile", phone);
        try {
            return (userInfo) this.jdbcTemplate.queryForObject(phoneSql, map, new BeanPropertyRowMapper(userInfo.class));
        } catch (Exception e) {
            return null;
        }

    }

}
