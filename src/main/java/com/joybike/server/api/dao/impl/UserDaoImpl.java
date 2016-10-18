package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserDao;
import com.joybike.server.api.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by 58 on 2016/10/12.
 */
@Repository("userDao")
public class UserDaoImpl extends Reository<User> implements UserDao {

    public void test() {
        User user = new User("lishaoyong",32,30);
        save(user);
    }
}
