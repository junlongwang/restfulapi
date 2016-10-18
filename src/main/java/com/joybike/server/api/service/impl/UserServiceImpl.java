package com.joybike.server.api.service.impl;

import com.joybike.server.api.dao.UserDao;
import com.joybike.server.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户体系服务
 * Created by 58 on 2016/10/18.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


}
