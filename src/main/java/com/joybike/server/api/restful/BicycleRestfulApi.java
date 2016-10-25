package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
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
    @RequestMapping(value = "subscribe", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> subscribe(@RequestParam("userId") long userId, @RequestParam("bicycleCode") String bicycleCode, @RequestParam("beginAt") int beginAt) {

        logger.info(userId + ":" + bicycleCode);

        try {
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.NoPay_Error.toString(), "null"));
            } else {
                bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                return ResponseEntity.ok(new Message<String>(true, null, ReturnEnum.Appointment_Success.getErrorDesc()));
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
            int cancle = bicycleRestfulService.deleteSubscribeInfo(userId, bicycleCode);
            if (cancle > 0)
                return ResponseEntity.ok(new Message<String>(true, null, ReturnEnum.Cancel_Success.getErrorDesc()));
            else return ResponseEntity.ok(new Message<String>(true, null, ReturnEnum.No_Subscribe.getErrorDesc()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Cancel_Error.toString(), null));
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
            return ResponseEntity.ok(new Message<List<vehicle>>(false, ReturnEnum.No_Vehicle.toString(), null));
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
            @RequestParam("beginLongitude") double beginLongitude,
            @RequestParam("beginDimension") double beginDimension) {

        logger.info(userId + ":" + bicycleCode);
        long orderId = 0;
        try {
            //获取是否有未支付订单
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.NoPay_Error.toString(), null));
            } else {
                orderId = bicycleRestfulService.unlock(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
            }

            if (orderId > 0) {
                return ResponseEntity.ok(new Message<String>(true, null, ReturnEnum.Unlock_Success.getErrorDesc()));
            } else {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Unlock_Error.toString(), null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Unlock_Error.toString(), null));
        }
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
    public ResponseEntity<Message<String>> submit(@RequestBody vehicleRepair form) {

        try {
            bicycleRestfulService.addVehicleRepair(form);
            return ResponseEntity.ok(new Message<String>(true, null, "提交成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Submit_Error.toString(), null));
        }


    }
}
