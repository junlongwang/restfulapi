package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.DisposeStatus;
import com.joybike.server.api.Enum.OrderStatus;
import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.*;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSClientUtil;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSConsts;
import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

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

    @Autowired
    private UserRestfulService userRestfulService;

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
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(subscribeDto.getUserId(), OrderStatus.end);
            double acountMoney = userRestfulService.getUserAcountMoneyByuserId(subscribeDto.getUserId());

            if (order != null) {
                return ResponseEntity.ok(new Message<subscribeInfo>(false, ReturnEnum.NoPay_Error.getErrorCode(), ReturnEnum.NoPay_Error.getErrorDesc(), null));
            } else {
                if (new BigDecimal(acountMoney).compareTo(BigDecimal.ZERO) == 0){
                    return ResponseEntity.ok(new Message<subscribeInfo>(false,  ReturnEnum.PayZero.getErrorCode(), ReturnEnum.PayZero.getErrorDesc(), null));
                }else{
                    try {
                        subscribeInfo info = bicycleRestfulService.vehicleSubscribe(subscribeDto.getUserId(), subscribeDto.getBicycleCode(), subscribeDto.getBeginAt());
                        return ResponseEntity.ok(new Message<subscribeInfo>(true, 0, ReturnEnum.Appointment_Success.getErrorDesc(), info));
                    } catch (Exception e) {
                        return ResponseEntity.ok(new Message<subscribeInfo>(false, ReturnEnum.UNKNOWN.getErrorCode(), ReturnEnum.UNKNOWN.getErrorDesc() + "-" + e.getMessage(), null));
                    }
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
    @RequestMapping(value = "cancle", method = RequestMethod.POST)
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
        vehicle vehicle= null;
        try {
            vehicle = bicycleRestfulService.getVehicleStatusByBicycleCode(bicycleCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        VehicleComHelper.find(vehicle.getBundlingPhone());
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
            vehicleOrder noPayorder = orderRestfulService.getNoPayOrderByUserId(unlockDto.getUserId(), OrderStatus.end);

            vehicleOrder useOrder = orderRestfulService.getNoPayOrderByUserId(unlockDto.getUserId(), OrderStatus.newly);

            double acountMoney = userRestfulService.getUserAcountMoneyByuserId(unlockDto.getUserId());

            VehicleOrderDto dto = new VehicleOrderDto();
            if (noPayorder != null) {
                return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.NoPay_Error.getErrorCode(), ReturnEnum.NoPay_Error.getErrorDesc(), null));
            } else {
                if (useOrder != null){
                    return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.UseNoTwo.getErrorCode(), ReturnEnum.UseNoTwo.getErrorDesc(), null));
                }else{
                    if (new BigDecimal(acountMoney).compareTo(BigDecimal.ZERO) == 0){
                        return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.PayZero.getErrorCode(), ReturnEnum.PayZero.getErrorDesc(), null));
                    }else{
                        dto = bicycleRestfulService.unlock(unlockDto.getUserId(), unlockDto.getBicycleCode(), unlockDto.getBeginAt(), unlockDto.getBeginLongitude(), unlockDto.getBeginDimension());
                    }
                }

            }

            if (dto != null) {
                vehicle vehicle=bicycleRestfulService.getVehicleStatusByBicycleCode(unlockDto.getBicycleCode());
                VehicleComHelper.openLock(vehicle.getBundlingPhone());
                //Thread.sleep(10*1000);
                //锁的状态是锁车状态
                while (!"1".equals(RedixUtil.getString(vehicle.getLockId().toString())))
                {
                    logger.info(vehicle.getLockId()+"等待车辆开锁...............");
                    Thread.sleep(1000);
                    continue;
                }
                logger.info(vehicle.getLockId()+"收到车辆开锁状态:"+RedixUtil.getString(vehicle.getLockId().toString()));
                return ResponseEntity.ok(new Message<VehicleOrderDto>(true, 0, ReturnEnum.Unlock_Success.getErrorDesc(), dto));
            } else {
                return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.Unlock_Error.getErrorCode(), ReturnEnum.Unlock_Error.getErrorDesc(), null));
            }
        } catch (RestfulException re){
            return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.Unlock_Error.getErrorCode(), ReturnEnum.Unlock_Error.getErrorDesc() + "-" + re.getMessage().toString(), null));
        }
        catch (Exception e) {
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
            form.setFaultImg(vehicleRepair.getFaultImg());
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
     * 上传车辆故障照片
     * @param bicycleCode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadRepairImg")
    @ResponseBody
    public ResponseEntity<Message<String>> uploadRepairImg(@RequestParam("bicycleCode") String bicycleCode,HttpServletRequest request) throws Exception {

        try {
            //获取解析器
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断是否是文件
            if(resolver.isMultipart(request)){
                //进行转换
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);
                //获取所有文件名称
                Iterator<String> it = multiRequest.getFileNames();
                while(it.hasNext()){
                    //根据文件名称取文件
                    MultipartFile file = multiRequest.getFile(it.next());
                    String fileName = file.getOriginalFilename();
                    String path = request.getSession().getServletContext().getRealPath("/");
                    String localPath = path + fileName;
                    logger.info("上传文件目录："+localPath);
//                    File newFile = new File(localPath);
//                    //上传的文件写入到指定的文件中
//                    file.transferTo(newFile);
                    String imageName = OSSClientUtil.uploadRepairImg(file.getInputStream());

                    return ResponseEntity.ok(new Message<String>(true, 0, null, imageName));
                }
            }
            return ResponseEntity.ok(new Message<String>(false, 0, null, "上传失败！"));
        }
        catch (Exception e)
        {
            logger.error("上传车辆故障图片报错：",e);
        }
        return ResponseEntity.ok(new Message<String>(false, 0, null, "上传失败！"));
    }

    /**
     *上传车辆停放位置图片
     * @param bicycleCode
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "uploadParkingImg")
    @ResponseBody
    public ResponseEntity<Message<String>> uploadParkingImg(@RequestParam("userId") long userId,@RequestParam("bicycleCode") String bicycleCode,@RequestParam("remark") String remark,HttpServletRequest request) throws Exception {

        try {
            //获取解析器
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //判断是否是文件
            if(resolver.isMultipart(request)){
                //进行转换
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);
                //获取所有文件名称
                Iterator<String> it = multiRequest.getFileNames();
                while(it.hasNext()){
                    //根据文件名称取文件
                    MultipartFile file = multiRequest.getFile(it.next());
                    String imageName = OSSClientUtil.uploadRepairImg(file.getInputStream());
                    bicycleRestfulService.updateVehicleImg(bicycleCode,imageName,remark);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, imageName));
                }
            }
            return ResponseEntity.ok(new Message<String>(false, 111, null, "上传车辆停放位置图片失败！"));
        }
        catch (Exception e)
        {
            logger.error("上传车辆停放位置图片报错：",e);
        }
        return ResponseEntity.ok(new Message<String>(false, 111, null, "上传车辆停放位置图片失败！"));
    }


    /**
     *支持H5 上传车辆停放位置图片
     * @param dto
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5UploadParkingImg",method =RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Message<String>> uploadParkingImg(@RequestBody uploadParkingImgDto dto) throws Exception {

        try {
            String imageName =dto.getParkingImg();
            bicycleRestfulService.updateVehicleImg(dto.getBicycleCode(),imageName,dto.getRemark());
            return ResponseEntity.ok(new Message<String>(true, 0, null, "上传成功"));
        }
        catch (Exception e)
        {
            logger.error("上传车辆停放位置图片报错：",e);
        }
        return ResponseEntity.ok(new Message<String>(false, 111, null, "上传车辆停放位置图片失败！"));
    }
}
