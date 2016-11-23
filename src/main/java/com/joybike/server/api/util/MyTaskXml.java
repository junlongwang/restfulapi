package com.joybike.server.api.util;

import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.service.BicycleRestfulService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lishaoyong on 16/11/15.
 */
public class MyTaskXml {

    @Autowired
    BicycleRestfulService restfulService;

    private final Logger logger = Logger.getLogger(MyTaskXml.class);

    public void show(){
        try {
            restfulService.deleteByExpire();
            logger.info("过期删除ok");
        } catch (Exception e) {
            logger.info("过期删除error");
        }

    }

}
