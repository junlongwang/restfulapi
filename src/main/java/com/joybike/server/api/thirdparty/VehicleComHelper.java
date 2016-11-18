package com.joybike.server.api.thirdparty;

import com.joybike.server.api.service.BicycleRestfulService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 车辆硬件通信
 * Created by 58 on 2016/10/21.
 */
public class VehicleComHelper {

    /**
     * 车辆开锁
     * @param mobile
     */
    public static SMSResponse openLock(String mobile)
    {
        return SMSSender.sendMessage(mobile,"00");
    }

    /**
     * 寻车辆
     * @param mobile
     */
    public static SMSResponse find(String mobile)
    {
        return SMSSender.sendMessage(mobile,"01");
    }

    /**
     * 唤醒车辆
     * @param mobile
     */
    public static SMSResponse wakeup(String mobile)
    {
        return SMSSender.sendMessage(mobile,"02");
    }
}


