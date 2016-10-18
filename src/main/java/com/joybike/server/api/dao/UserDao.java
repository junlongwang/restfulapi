package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.po.userInfo;

/**
 * Created by 58 on 2016/10/12.
 */
public interface UserDao extends IRepository<userInfo> {
//    public void test();


    int update();


}
