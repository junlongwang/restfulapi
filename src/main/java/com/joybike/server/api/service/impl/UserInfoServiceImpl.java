package com.joybike.server.api.service.impl;


import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.service.UserInfoService;
import com.joybike.server.api.util.MergeUtil;
import com.joybike.server.api.util.StringRandom;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户体系服务
 * Created by 58 on 2016/10/18.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;


    /**
     * 修改用户基本信息，先获取用户所有的信息
     *
     * @param user
     * @return
     */
    public int updateUserInfo(userInfo user) {

        long userId = user.getId();

        userInfo info = userInfoDao.getUserInfo(userId);

        try {
            MergeUtil.merge(info, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoDao.update(user);

    }

    /**
     * 根据手机号获取用户信息,如果用户存在则返回用户信息，不存在则创建用户信息并返回信息
     *
     * @param phone
     * @return
     */
    public userInfo getUserInfoByMobile(String phone) {

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
    }


}
