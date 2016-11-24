package com.joybike.server.api.util;

import com.joybike.server.api.Enum.UseStatus;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.model.subscribeInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lishaoyong on 16/11/15.
 */
@Service
public class MyTaskXml {

    @Autowired
    SubscribeInfoDao subscribeInfoDao;

    @Autowired
    VehicleDao vehicleDao;

    Logger logger = Logger.getLogger(MyTaskXml.class);

    public void show(){
        try {
            List<subscribeInfo> list = subscribeInfoDao.getByTime();
            if (list != null && list.size() >0){
                for (int i = 0 ; i < list.size() ; i++){
                    if (list.get(i).getEndAt() < UnixTimeUtils.now()){
                        vehicleDao.updateVehicleUseStatus(list.get(i).getVehicleId(), UseStatus.free);
                        subscribeInfoDao.delete(list.get(i).getId());
                        logger.info(list.get(i));
                    }
                }
            }

        } catch (Exception e) {
            logger.info("过期删除error");
        }

    }

}
