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
    final String getUserInfoSql = "select * from userInfo where id = :userId";

    @Override
    public userInfo getUserInfo(long userId) {
        Map map = new HashMap();
        map.put("userId", userId);
        return (userInfo) this.jdbcTemplate.queryForObject(getUserInfoSql, map, new BeanPropertyRowMapper(userInfo.class));
    }


    /**
     * 根据用户手机号码获取用户信息
     *
     * @param phone
     * @return
     */
    final String getPhoneSql = "select * from userInfo where iphone =?";

    @Override
    public userInfo getInfoByPhone(String phone) {
        //Map map = new HashMap();
        //map.put("iphone", phone);
        //return (userInfo) this.jdbcTemplate.queryForObject(getPhoneSql, map, new BeanPropertyRowMapper(userInfo.class));

        Object[] object = new Object[]{phone};
        List<userInfo> list = this.jdbcTemplate.getJdbcOperations().query(getPhoneSql, object, new BeanPropertyRowMapper(userInfo.class));

        if(list!=null && list.size()>0)
        {
            return list.get(0);
        }
        return null;
    }

}
