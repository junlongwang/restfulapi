package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.DisposeStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.vehicleGpsDataDto;
import com.joybike.server.api.dto.vehicleRepairDto;
import com.joybike.server.api.model.Message;
import com.joybike.server.api.model.vehicle;
import com.joybike.server.api.model.vehicleHeartbeat;
import com.joybike.server.api.model.vehicleRepair;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSClientUtil;
import com.joybike.server.api.thirdparty.wxtenpay.util.DateUtil;
import com.joybike.server.api.util.UnixTimeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by 58 on 2016/10/31.
 */
@RestController()
public class BikeDataUploadRestfulApi {

    private final Logger logger = Logger.getLogger(BicycleRestfulApi.class);

    static {
        //DOMConfigurator.configure("/opt/soft/log4j.xml");
    }

    @Autowired
    private VehicleHeartbeatDao vehicleHeartbeatDao;

    @Autowired
    private VehicleDao vehicleDao;

    /**
     * 车辆，车锁GPS,每隔15秒上报数据，回调地址服务端回调地址
     *
     * @param param
     */
    @RequestMapping(value = "post", method = RequestMethod.POST)
    public void lockCallBack(@RequestBody String param) {


        logger.info(param);

        try {
            String token = param.split(";")[0];
            String content = param.split(";")[1];
            String[] values = content.split(",");

            vehicleHeartbeat heartbeat = new vehicleHeartbeat();

            heartbeat.setLockId(Long.valueOf(values[0]));
            heartbeat.setFirmwareVersion(values[1]);
            heartbeat.setAllocation(values[2]);
            heartbeat.setBaseStationType(values[3]);
            if (values[3].equals("0")) {
                heartbeat.setGpsTime(Long.valueOf(values[4]));
                heartbeat.setDimension(BigDecimal.valueOf(Double.valueOf(values[5])));
                heartbeat.setLongitude(BigDecimal.valueOf(Double.valueOf(values[6])));
            }
            if (values[3].equals("1")) {
                heartbeat.setLockTime(Long.valueOf(values[4]));
                heartbeat.setCellId(values[5]);
                heartbeat.setStationId(values[6]);
            }

            heartbeat.setSpeed(values[7]);
            heartbeat.setDirection(values[8]);
            heartbeat.setArousalType(Integer.valueOf(values[9]));
            heartbeat.setCustom(values[10]);
            heartbeat.setLockStatus(Integer.valueOf(values[11]));
            heartbeat.setBatteryStatus(Integer.valueOf(values[12]));
            heartbeat.setBatteryPercent(values[13]);
            heartbeat.setCreateAt(UnixTimeUtils.now());
            vehicleHeartbeatDao.save(heartbeat);
        } catch (Exception e) {
            logger.error("车锁GPS,每隔15秒上报数据发生异常：" + e.getMessage(), e);
        }
    }

    /**
     * 用户手机卡，每隔15秒上报数据
     *
     * @param data
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public void send(@RequestBody vehicleGpsDataDto data) {

        try {
            Long lockId = null;
            try {
                lockId = vehicleDao.getLockByBicycleCode(data.getBicycleCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            vehicleHeartbeat vehicleHeartbeat = new vehicleHeartbeat();
            vehicleHeartbeat.setLockId(lockId);
            vehicleHeartbeat.setLongitude(data.getLongitude());
            //GPS的纬度
            vehicleHeartbeat.setDimension(data.getDimension());
            vehicleHeartbeat.setCreateAt(UnixTimeUtils.now());
            vehicleHeartbeatDao.save(vehicleHeartbeat);
        }
        catch (Exception e)
        {
            logger.error("用户手机卡，每隔15秒上报数据出现异常：",e);
        }
    }
}
