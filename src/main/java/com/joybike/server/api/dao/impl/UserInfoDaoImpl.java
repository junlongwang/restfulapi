package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.dto.UserDto;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.util.RestfulException;
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
     * 获取用户基本信息
     *
     * @param userId
     * @return
     */
    final String userInfoSql = "select * from userInfo where id = :userId";

    @Override
    public userInfo getUserInfo(long userId) throws Exception {
        Map map = new HashMap();
        map.put("userId", userId);
        try {
            try {
                return (userInfo) this.jdbcTemplate.queryForObject(userInfoSql, map, new BeanPropertyRowMapper(userInfo.class));
            } catch (Exception e) {
                return null;
            }
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
    final String phoneSql = "select * from userInfo where iphone = :phone";

    @Override
    public userInfo getInfoByPhone(String phone) throws Exception {
        Map map = new HashMap();
        map.put("phone", phone);
        try {
            try {
                return (userInfo) this.jdbcTemplate.queryForObject(phoneSql, map, new BeanPropertyRowMapper(userInfo.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }

    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    final String getUserInfoByIdSql = "select * from userInfo where id = :userId";

    @Override
    public UserDto getUserInfoById(long userId) throws Exception {
        Map map = new HashMap();
        map.put("userId", userId);
        try {
            try {
                return (UserDto) this.jdbcTemplate.queryForObject(getUserInfoByIdSql, map, new BeanPropertyRowMapper(UserDto.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

}
