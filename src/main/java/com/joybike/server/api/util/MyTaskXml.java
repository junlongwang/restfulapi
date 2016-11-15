package com.joybike.server.api.util;

import com.joybike.server.api.dao.SubscribeInfoDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lishaoyong on 16/11/15.
 */
public class MyTaskXml {

    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    private final Logger logger = Logger.getLogger(MyTaskXml.class);

    public void show(){
        try {
            subscribeInfoDao.deleteByExpire();
            logger.info("过期删除ok");
        } catch (Exception e) {
            logger.info("过期删除error");
        }

    }

}
