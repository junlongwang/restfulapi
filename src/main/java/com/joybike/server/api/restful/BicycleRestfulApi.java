package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.DisposeStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.vehicleRepairDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSClientUtil;
import com.joybike.server.api.util.UnixTimeUtils;
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
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.NoPay_Error.getErrorCode(), ReturnEnum.NoPay_Error.getErrorDesc(),null));
            } else {
                try {
                    bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                    return ResponseEntity.ok(new Message<String>(true, 0, null,ReturnEnum.Appointment_Success.getErrorDesc()));
                } catch (Exception e) {
                    return ResponseEntity.ok(new Message<String>(false,ReturnEnum.UNKNOWN.getErrorCode(),ReturnEnum.UNKNOWN.getErrorDesc()+"-"+e.getMessage(), null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Appointment_Error.getErrorCode(), ReturnEnum.Appointment_Error.getErrorDesc()+"-"+e.getMessage(),null));
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
            return ResponseEntity.ok(new Message<String>(true, 0,null,"操作成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Cancel_Success.getErrorCode(),ReturnEnum.Cancel_Success.getErrorDesc()+"-"+e.getMessage(),null));
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
        return ResponseEntity.ok(new Message<String>(true,0,null,"寻车成功！"));
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
            return ResponseEntity.ok(new Message<List<vehicle>>(true, 0,null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<vehicle>>(false, ReturnEnum.UNKNOWN.getErrorCode(),ReturnEnum.UNKNOWN.getErrorDesc()+"-"+e.getMessage(), null));
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
            @RequestParam("beginLongitude") Double beginLongitude,
            @RequestParam("beginDimension") Double beginDimension) {

        logger.info(userId + ":" + bicycleCode);
        try {
            long orderId = 0;

            //获取是否有未支付订单
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.NoPay_Error.getErrorCode(), ReturnEnum.NoPay_Error.getErrorDesc(),null));
            } else {
                orderId = bicycleRestfulService.unlock(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
            }

            if (orderId > 0) {
                return ResponseEntity.ok(new Message<String>(true, 0,null, "操作成功！"));
            } else {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Unlock_Error.getErrorCode(),ReturnEnum.Unlock_Error.getErrorDesc(),null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Unlock_Error.getErrorCode(), ReturnEnum.Unlock_Error.getErrorDesc()+"-"+e.getMessage(),null));
        }
    }

    /**
     * 车锁GPS,每隔15秒上报数据，回调地址服务端回调地址
     *
     * @param param
     */
    @RequestMapping(value = "callback", method = RequestMethod.POST)
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
        }catch (Exception e){
            logger.error("车锁GPS,每隔15秒上报数据发生异常："+e.getMessage(),e);
        }
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
     * @param vehicleRepair
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    public ResponseEntity<Message<String>> submit(@RequestBody vehicleRepairDto vehicleRepair) {

        try {
            vehicleRepair form = new vehicleRepair();
            form.setVehicleId(vehicleRepair.getBicycleCode());
            form.setCause(vehicleRepair.getCause());

            if (vehicleRepair.getFaultImg() != null && vehicleRepair.getFaultImg().length > 0) {
                String imageName = OSSClientUtil.uploadRepairImg(vehicleRepair.getFaultImg());
                form.setFaultImg(imageName);
            }
            form.setCreateId(vehicleRepair.getCreateId());
            form.setCreateAt(vehicleRepair.getCreateAt());
            form.setDisposeStatus(DisposeStatus.untreated.getValue());
            bicycleRestfulService.addVehicleRepair(form);
            return ResponseEntity.ok(new Message<String>(true, 0,null, "提交成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Submit_Error.getErrorCode(), ReturnEnum.Submit_Error.getErrorDesc()+"-"+e.getMessage(),null));
        }
    }



}
