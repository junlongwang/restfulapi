package com.joybike.server.api.service.impl;


import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.service.UserInfoService;
import com.joybike.server.api.util.MergeUtil;
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
     * @param user
     * @return
     */
    public int updateUserInfo(userInfo user) {

        long userId = user.getId();

        userInfo info = userInfoDao.getUserInfo(userId);

        try {
            MergeUtil.merge(info,user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user + "：user的信息");

        userInfoDao.updateUserInfo(user);

        return 0;
    }

    @Override
    public userInfo getUserInfoByMobile(String mobile) {
        userInfo userInfo= userInfoDao.getUserInfoByMobile(mobile);
        if(userInfo==null)
        {
            userInfo = new userInfo();
            userInfo.setIphone(mobile);
            long userId = userInfoDao.save(userInfo);
            userInfo.setId(userId);
        }
        return userInfo;
    }

    @Override
    public double getUserAcountMoneyByuserId(long userId){

        return 0.01;
    }
}
