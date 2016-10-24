package com.joybike.server.api.service.impl;

import com.joybike.server.api.Enum.ErrorEnum;
import com.joybike.server.api.dao.BankAcountDao;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.MergeUtil;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.StringRandom;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lishaoyong on 16/10/23.
 */
@Service
public class UserRestfulServiceImpl implements UserRestfulService {

    @Autowired
    private BankAcountDao acountDao;


    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * 根据userId获取用户余额
     *
     * @param userId
     * @return
     */
    @Override
    public double getUserAcountMoneyByuserId(long userId) throws Exception {

        try {
            return acountDao.getUserAmount(userId);
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.SERVICE_ERROR);
        }
    }

    /**
     * 修改用户基本信息，先获取用户所有的信息
     *
     * @param user
     * @return
     */
    @Override
    public int updateUserInfo(userInfo user) throws Exception {

        try {
            long userId = user.getId();
            userInfo info = userInfoDao.getUserInfo(userId);
            MergeUtil.merge(info, user);
            return userInfoDao.update(user);
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.SERVICE_ERROR);
        }

    }

    /**
     * 根据手机号获取用户信息,如果用户存在则返回用户信息，不存在则创建用户信息并返回信息
     *
     * @param phone
     * @return
     */
    @Override
    public userInfo getUserInfoByMobile(String phone) throws Exception {

        try {
            userInfo userInfo = userInfoDao.getInfoByPhone(phone);
            if (userInfo != null) {
                return userInfo;
            } else {
                userInfo user = new userInfo();
                user.setRealName(StringRandom.getStringRandom(8));
                user.setIphone(phone);
                user.setSecurityStatus(0);
                user.setAuthenStatus(0);
                user.setUpdateAt(0);
                user.setCreateAt(UnixTimeUtils.now());
                long userId = userInfoDao.save(user);
                return userInfoDao.getUserInfo(userId);
            }
        } catch (Exception e) {
            throw new RestfulException(ErrorEnum.SERVICE_ERROR);
        }


    }
}
