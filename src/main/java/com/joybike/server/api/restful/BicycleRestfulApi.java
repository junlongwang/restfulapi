package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.DisposeStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.*;
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
    private BicycleRestfulService bicycleRestfulService;

    @Autowired
    private OrderRestfulService orderRestfulService;


    /**
     * 预约车辆
     *
     * @param subscribeDto
     * @return
     */
    @RequestMapping(value = "subscribe", method = RequestMethod.POST)
    public ResponseEntity<Message<subscribeInfo>> subscribe(@RequestBody SubscribeDto subscribeDto) {

        logger.info(subscribeDto.getUserId() + ":" + subscribeDto.getBicycleCode() + ":" + subscribeDto.getBeginAt());

        try {
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(subscribeDto.getUserId());

            if (order != null) {
                return ResponseEntity.ok(new Message<subscribeInfo>(false, ReturnEnum.NoPay_Error.getErrorCode(), ReturnEnum.NoPay_Error.getErrorDesc(), null));
            } else {
                try {
                    subscribeInfo info = bicycleRestfulService.vehicleSubscribe(subscribeDto.getUserId(), subscribeDto.getBicycleCode(), subscribeDto.getBeginAt());
                    return ResponseEntity.ok(new Message<subscribeInfo>(true, 0, ReturnEnum.Appointment_Success.getErrorDesc(), info));
                } catch (Exception e) {
                    return ResponseEntity.ok(new Message<subscribeInfo>(false, ReturnEnum.UNKNOWN.getErrorCode(), ReturnEnum.UNKNOWN.getErrorDesc() + "-" + e.getMessage(), null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<subscribeInfo>(false, ReturnEnum.Appointment_Error.getErrorCode(), ReturnEnum.Appointment_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }


    /**
     * 取消预约车辆
     *
     * @param cancleDto
     * @return
     */
    @RequestMapping(value = "cancle", method = RequestMethod.GET)
    public ResponseEntity<Message<String>> cancle(@RequestBody CancleDto cancleDto) {
        try {
            bicycleRestfulService.deleteSubscribeInfo(cancleDto.getUserId(), cancleDto.getBicycleCode());
            return ResponseEntity.ok(new Message<String>(true, 0, null, ReturnEnum.Cancel_Success.getErrorDesc()));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Cancel_Success.getErrorCode(), ReturnEnum.Cancel_Success.getErrorDesc() + "-" + e.getMessage(), null));
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
        return ResponseEntity.ok(new Message<String>(true, 0, null, "寻车成功！"));
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
            List<vehicle> list = bicycleRestfulService.getVehicleList(dimension, longitude);
            return ResponseEntity.ok(new Message<List<vehicle>>(true, 0, null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<vehicle>>(false, ReturnEnum.UNKNOWN.getErrorCode(), ReturnEnum.UNKNOWN.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }


    /**
     * 扫描开锁
     *
     * @param unlockDto
     */
    @RequestMapping(value = "unlock", method = RequestMethod.POST)
    public ResponseEntity<Message<VehicleOrderDto>> unlock(@RequestBody UnlockDto unlockDto) {

        logger.info(unlockDto.toString());
        try {
            long orderId = 0;

            //获取是否有未支付订单
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(unlockDto.getUserId());
            VehicleOrderDto dto = new VehicleOrderDto();
            if (order != null) {
                return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.NoPay_Error.getErrorCode(), ReturnEnum.NoPay_Error.getErrorDesc(), null));
            } else {
                 dto = bicycleRestfulService.unlock(unlockDto.getUserId(), unlockDto.getBicycleCode(), unlockDto.getBeginAt(), unlockDto.getBeginLongitude(), unlockDto.getBeginDimension());
            }

            if (dto != null) {
                return ResponseEntity.ok(new Message<VehicleOrderDto>(true, 0, ReturnEnum.Unlock_Success.getErrorDesc(), dto));
            } else {
                return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.Unlock_Error.getErrorCode(), ReturnEnum.Unlock_Error.getErrorDesc(), null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.Unlock_Error.getErrorCode(), ReturnEnum.Unlock_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
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
            com.joybike.server.api.model.vehicleRepair form = new vehicleRepair();
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
            return ResponseEntity.ok(new Message<String>(true, 0, null, "提交成功！"));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Submit_Error.getErrorCode(), ReturnEnum.Submit_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 使用信息
     *
     * @param userId
     */
    @RequestMapping(value = "useInfo", method = RequestMethod.GET)
    public ResponseEntity<Message<VehicleOrderSubscribeDto>> useInfo(long userId) {

        logger.info(userId);
        try {

            VehicleOrderSubscribeDto dto = bicycleRestfulService.getUseInfo(userId);

            if (dto.getVehicleOrderDto() != null && dto.getInfo() != null){
                if (dto.getVehicleOrderDto().getStatus() == 1){
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 0,null, dto));
                }else{
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 4,null, dto));
                }

            }else if (dto.getVehicleOrderDto() != null && dto.getInfo() == null){
                if (dto.getVehicleOrderDto().getStatus() == 1){
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 0,null, dto));
                }else{
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 4,null, dto));
                }
            }else if (dto.getVehicleOrderDto() == null && dto.getInfo() != null){
                return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 1,null, dto));
            }else{
                return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 2,null, null));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(false,3, e.getMessage(), null));
        }
    }

}
