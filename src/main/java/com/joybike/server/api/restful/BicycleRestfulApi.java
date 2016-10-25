package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.DisposeStatus;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.dto.vehicleRepairDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 58 on 2016/10/16.
 */
@RequestMapping("/bicycle")
@RestController()
public class BicycleRestfulApi {

    private final Logger logger = Logger.getLogger(BicycleRestfulApi.class);

    static {
        //DOMConfigurator.configure("/opt/soft/log4j.xml");
    }

    @Autowired
    private VehicleHeartbeatDao vehicleHeartbeatDao;

    @Autowired
    private BicycleRestfulService bicycleRestfulService;

    @Autowired
    private OrderRestfulService orderRestfulService;


    /**
     * 预约车辆
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "subscribe", method = RequestMethod.GET)
    public ResponseEntity<Message<String>> subscribe(@RequestParam("userId") long userId, @RequestParam("bicycleCode") String bicycleCode, @RequestParam("beginAt") int beginAt) {

        logger.info(userId + ":" + bicycleCode);

        try {
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
                return ResponseEntity.ok(new Message<String>(false, "1001：" + "有未支付的订单", "null"));
            } else {
                try {
                    bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                    return ResponseEntity.ok(new Message<String>(true, null, "预约成功！"));
                } catch (Exception e) {
                    return ResponseEntity.ok(new Message<String>(false, e.getMessage(), null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, e.getMessage(), null));
        }
    }


    /**
     * 取消预约车辆
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "cancle", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> cancle(@RequestParam("userId") long userId, @RequestParam("bicycleCode") String bicycleCode) {
        try {
            bicycleRestfulService.deleteSubscribeInfo(userId, bicycleCode);
            return ResponseEntity.ok(new Message<String>(true, null, "取消预约成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, "取消预约失败", null));
        }
    }

    /**
     * 寻车
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "lookup", method = RequestMethod.GET)
    public ResponseEntity<Message<String>> lookup(@RequestParam("userId") long userId, @RequestParam("bicycleCode") String bicycleCode) {
        VehicleComHelper.find(bicycleCode);
        return ResponseEntity.ok(new Message<String>(true, null, "寻车成功！"));
    }

    /**
     * 获取可用闲置车辆，一公里以内
     *
     * @param longitude
     * @param dimension
     * @return
     */
    @RequestMapping(value = "available", method = RequestMethod.GET)
    public ResponseEntity<Message<List<vehicle>>> getAvailable(double longitude, double dimension) {
        try {
            List<vehicle> list = bicycleRestfulService.getVehicleList(longitude, dimension);
            return ResponseEntity.ok(new Message<List<vehicle>>(true, null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<vehicle>>(false, "1001：" + "GPS信号丢失", null));
        }
    }


    /**
     * 扫描开锁
     *
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "unlock", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> unlock(
            @RequestParam("userId") long userId,
            @RequestParam("bicycleCode") String bicycleCode,
            @RequestParam("beginAt") int beginAt,
            @RequestParam("beginDimension") BigDecimal beginDimension,
            @RequestParam("beginLongitude") BigDecimal beginLongitude) {

        logger.info(userId + ":" + bicycleCode);
        try {
            //获取是否有未支付订单
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);
            //获取使用状态
            int useStatus = bicycleRestfulService.getVehicleUseStatusByBicycleCode(bicycleCode);
            //获取车的状态
            int status = bicycleRestfulService.getVehicleStatusByBicycleCode(bicycleCode);

            if (order == null) {
                if (status == 0) {
                    if (useStatus == 1) {
                        return ResponseEntity.ok(new Message<String>(false, "1001：" + "该车已被预约", "null"));
                    }
                    if (useStatus == 2) {
                        return ResponseEntity.ok(new Message<String>(false, "1001：" + "该车正在使用中", "null"));
                    }
                    if (useStatus == 0) {
                        VehicleComHelper.openLock(bicycleCode);
                        orderRestfulService.addOrder(userId, bicycleCode, beginAt, beginDimension, beginLongitude);
                    }
                }
                if (status == 1) {
                    return ResponseEntity.ok(new Message<String>(false, "1001：" + "该车为禁用状态,不可使用", "null"));
                }
                if (status == 2) {
                    return ResponseEntity.ok(new Message<String>(false, "1001：" + "该车故障中,不可使用", "null"));
                }

            } else {
                return ResponseEntity.ok(new Message<String>(false, "1001：" + "有订单未支付,请完成支付", "null"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, "开锁失败", null));
        }


        return ResponseEntity.ok(new Message<String>(true, null, "开锁成功！"));
    }

    /**
     * 锁车动作，服务端回调地址
     *
     * @param param
     */
    @RequestMapping(value = "lockCall", method = RequestMethod.POST)
    public void lockCallBack(@RequestBody String param) {


        logger.info(param);


        String token = param.split(";")[0];

        String content = param.split(";")[1];
        String[] values = content.split(",");

        vehicleHeartbeat heartbeat = new vehicleHeartbeat();

        heartbeat.setLockId(Long.valueOf(values[0]));
        heartbeat.setFirmwareVersion(values[1]);
        heartbeat.setAllocation(values[2]);
        heartbeat.setBaseStationType(values[3]);
        if (values[3] == "0") {
            heartbeat.setGpsTime(Integer.valueOf(values[4]));
            heartbeat.setDimension(BigDecimal.valueOf(Double.valueOf(values[5])));
            heartbeat.setLongitude(BigDecimal.valueOf(Double.valueOf(values[6])));
        }
        if (values[3] == "1") {
            heartbeat.setLockTime(Integer.valueOf(values[4]));
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
        //heartbeat.setCreateAt();
        vehicleHeartbeatDao.save(heartbeat);

    }

    /**
     * 车上手机卡，每隔15秒上报数据，回调地址
     *
     * @param data
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public void send(@RequestBody vehicleHeartbeat data) {

    }


    /**
     * 提交故障车辆信息
     *
     * @param form
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> submit(@RequestBody vehicleRepairDto vehicleRepair) {

        try {
            vehicleRepair form =new vehicleRepair();
            form.setVehicleId(vehicleRepair.getBicycleCode());
            form.setCause(vehicleRepair.getCause());
            form.setFaultImg("");
            form.setCreateId(vehicleRepair.getCreateId());
            form.setCreateAt(vehicleRepair.getCreateAt());
            form.setDisposeStatus(DisposeStatus.untreated.getValue());
            bicycleRestfulService.addVehicleRepair(form);
            return ResponseEntity.ok(new Message<String>(true, null, "提交成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, "1001：" + "故障申报失败", null));
        }


    }
}
