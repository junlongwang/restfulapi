package com.joybike.server.api.restful;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSResponse;
import com.joybike.server.api.Enum.DisposeStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.UserDto;
import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.dto.vehicleGpsDataDto;
import com.joybike.server.api.dto.vehicleRepairDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSClientUtil;
import com.joybike.server.api.thirdparty.aliyun.pushHelper;
import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
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
import java.util.DoubleSummaryStatistics;

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

    @Autowired
    private OrderRestfulService orderRestfulService;


    @Autowired
    private UserRestfulService userRestfulService;

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

            //锁是开锁状态
            if(heartbeat.getLockStatus()==1)
            {
                //放入REDIS ，车锁状态，便于开锁真正获取
                RedixUtil.setString(heartbeat.getLockId().toString(),"1");
                //锁车，重置锁车量
                RedixUtil.setString(heartbeat.getLockId() + "_lockCount","0");
            }
            else//锁车状态
            {
                RedixUtil.setString(heartbeat.getLockId().toString(),"0");
            }

            logger.info("车辆现在状态（0：锁车；1：开锁。）：" + heartbeat.getLockStatus());
            logger.info("车辆锁车状态次数：" + RedixUtil.getString(heartbeat.getLockId() + "_lockCount"));

            //车辆锁车 0:锁车， 1 ： 开锁
            if(heartbeat.getLockStatus()==0 || heartbeat.getLockStatus().equals(0)) //锁是锁车状态
            {
                //设置车锁车状态计数，如果多次，仅结束行程一次
                String lockCount=RedixUtil.getString(heartbeat.getLockId() + "_lockCount");
                if(lockCount==null || "".equals(lockCount))
                {
                    RedixUtil.setString(heartbeat.getLockId() + "_lockCount","1");
                }
                else
                {
                    Long count= Long.valueOf(lockCount)+1;
                    RedixUtil.setString(heartbeat.getLockId() + "_lockCount",count.toString());
                }
                String temp = RedixUtil.getString(heartbeat.getLockId() + "_lockCount");
                if(temp.equals("1") || temp=="1") {
                    vehicle vehicle = vehicleDao.getVehicleBylockId(heartbeat.getLockId().toString());
                    //使用中
                    if(vehicle.getUseStatus()==2) {
                        UserPayIngDto dto = orderRestfulService.userPayOrder(vehicle.getVehicleId(), UnixTimeUtils.now(), Double.valueOf("116.287"), Double.valueOf("40.043"));
                        //消息推送
                        logger.info("--------------------消息推送--------------------");
                        logger.info(JSON.toJSONString(dto));
                        logger.info("--------------------消息推送--------------------");
                        UserDto userDto = userRestfulService.getUserInfoById(dto.getVehicleOrderDto().getUserId());
                        if(userDto.getTargetType().equals("ios")) {
                            pushHelper.PushMessageToIOS(JSON.toJSONString(dto), userDto.getGuid());
                        }
                        else if(userDto.getTargetType().equals("android")){
                            pushHelper.PushMessageToAndroid(JSON.toJSONString(dto), userDto.getGuid());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("车锁GPS,每隔15秒上报数据发生异常：" + e.getMessage(), e);
        }
    }

    /**
     * 用户app上报GPS数据，每隔15秒上报数据
     *
     * @param data
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public void send(@RequestBody vehicleGpsDataDto data) {

        logger.info("$$$$$$$$$$$$$$$$$$$send$$$$$$$$$$$$$$$$$$$$$$$$4");
        logger.info(data);
        logger.info("$$$$$$$$$$$$$$$$$$$$send$$$$$$$$$$$$$$$$$$$$$$$4");
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
