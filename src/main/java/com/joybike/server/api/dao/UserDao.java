package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.User;

/**
 * Created by 58 on 2016/10/12.
 */
public interface UserDao extends IRepository<User> {
    public void test();
}
