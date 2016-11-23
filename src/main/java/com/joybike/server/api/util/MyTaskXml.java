package com.joybike.server.api.util;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dao.VehicleOrderDao;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.service.BicycleRestfulService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by lishaoyong on 16/11/15.
 */
public class MyTaskXml {

    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    @Autowired
    VehicleDao vehicleDao;

    private final Logger logger = Logger.getLogger(MyTaskXml.class);

    public void show(){
        try {
            List<subscribeInfo> list = subscribeInfoDao.getSqlByTime(UnixTimeUtils.now());


            if (list != null) {
                list.forEach(new Consumer<subscribeInfo>() {
                    @Override
                    public void accept(subscribeInfo subscribeInfo) {
                        try {
                            logger.info(subscribeInfo);
                            vehicleDao.updateVehicleUseStatus(subscribeInfo.getVehicleId(), UseStatus.free);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                subscribeInfoDao.deleteByExpire();
                logger.info("过期删除ok");
            }

        } catch (Exception e) {
            logger.info("过期删除error");
        }

    }

}
