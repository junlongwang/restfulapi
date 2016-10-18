package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.UserDao;
import com.joybike.server.api.model.userInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by 58 on 2016/10/12.
 */
@Repository("userDao")
public class UserDaoImpl extends Reository<userInfo> implements UserDao {

    /**
     * 用户注册
     * @return
     */
    public int update() {

        return 0;
    }

//    public void test() {
//        User user = new User("lishaoyong",32,30);
//        save(user);
//    }


}
