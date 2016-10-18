package com.joybike.server.api.service.impl;

import com.joybike.server.api.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 58 on 2016/10/18.
 */
@Service
public class UserServiceImpl {

    @Autowired
    private UserDao userDao;


}
