package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.smsDao;
import com.joybike.server.api.dto.*;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.SMSHelper;
import com.joybike.server.api.thirdparty.SMSResponse;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSClientUtil;
import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by 58 on 2016/10/16.
 */
@RequestMapping("/user")
@RestController()
public class UserRestfulApi {

    private final Logger logger = Logger.getLogger(UserRestfulApi.class);

    @Autowired
    private UserRestfulService userRestfulService;

    @Autowired
    private BicycleRestfulService bicycleRestfulService;

    @Autowired
    private PayRestfulService payRestfulService;

    @Autowired
    private OrderRestfulService orderRestfulService;

    @Autowired
    private smsDao smsDao;


    /**
     * 更新用户信息
     *
     * @param userInfoDto
     * @return
     */
    //@SystemControllerLog(description = "更新用户信息")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<Message<UserDto>> update(@RequestBody userInfoDto userInfoDto) {
        try {
            userInfo user = new userInfo();
            user.setId(userInfoDto.getUserId());
            user.setIphone(userInfoDto.getIphone());
            user.setIdNumber(userInfoDto.getIdNumber());
            user.setRealName(userInfoDto.getRealName());
            if(userInfoDto.getIdNumber()!=null && !"".equals(userInfoDto.getIdNumber()) && userInfoDto.getRealName()!=null && !"".equals(userInfoDto.getRealName()))
            {
                user.setAuthenStatus(1);
            }
            user.setIdentityCardphoto(userInfoDto.getIdentityCardphoto());
            user.setPhoto(userInfoDto.getPhoto());
            user.setUserImg(userInfoDto.getUserImg());

            user.setNationality(userInfoDto.getNationality());
            user.setGuid(userInfoDto.getGuid());
            user.setOpenId(userInfoDto.getOpenId());
            user.setTargetType(userInfoDto.getTargetType());
            userRestfulService.updateUserInfo(user);
            userInfo u = userRestfulService.getUserInfoByMobile(user.getIphone());
            UserDto userInfo = userRestfulService.getUserInfoById(u.getId());
            return ResponseEntity.ok(new Message<UserDto>(true, 0, null, userInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<UserDto>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), ReturnEnum.UpdateUer_ERROR.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 获取手机验证码
     *
     * @param mobile 手机号码
     * @return
     */
    //@SystemControllerLog(description = "获取手机验证码")
    @RequestMapping(value = "getValidateCode", method = RequestMethod.GET)
    public ResponseEntity<Message<String>> getValidateCode(@RequestParam("mobile") String mobile) {
        int randNo = 0;
        try {
            //如果验证码没有过期，不允许重复请求发送
            if (RedixUtil.exits(mobile)) {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Iphone_Sender_Error.getErrorCode(), ReturnEnum.Iphone_Sender_Error.getErrorDesc(), null));
            }
            randNo = new Random().nextInt(9999 - 1000 + 1) + 1000;

            //发送短信接口
            SMSResponse smsResponse = SMSHelper.sendValidateCode(mobile, String.valueOf(randNo));
            if (!smsResponse.getErrorCode().equals("0")) {
                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Iphone_Error.getErrorCode(), ReturnEnum.Iphone_Error.getErrorDesc() + "-" + smsResponse.getMsg(), null));
            } else {
                //存放到REDIX
                RedixUtil.setString(mobile, String.valueOf(randNo), 60);
            }
            return ResponseEntity.ok(new Message<String>(true, 0, null, null));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UNKNOWN.getErrorCode(), ReturnEnum.UNKNOWN.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 获取用户账户余额
     *
     * @param userId
     * @return
     */
    //@SystemControllerLog(description = "获取用户账户余额")
    @RequestMapping(value = "getAcountMoney", method = RequestMethod.GET)
    public ResponseEntity<Message<Double>> getAcountMoney(@RequestParam("userId") long userId) {
        try {
            double acountMoney = userRestfulService.getUserAcountMoneyByuserId(userId);
            return ResponseEntity.ok(new Message<Double>(true, 0, null, acountMoney));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<Double>(false, ReturnEnum.Acount_Error.getErrorCode(), ReturnEnum.Acount_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 获取系统推送信息
     *
     * @return
     */
    //@SystemControllerLog(description = "获取系统推送信息")
    @RequestMapping(value = "getMessages", method = RequestMethod.GET)
    public ResponseEntity<Message<List<sms>>> getMessages(@RequestParam("userId") long userId) {
        List<sms> lst =  smsDao.getSmsMessages(userId);
        return ResponseEntity.ok(new Message<List<sms>>(true, 0, null, lst));
    }


    /**
     * 验证码验证登录
     *
     * @param dto
     * @return
     */
    //@SystemControllerLog(description = "验证码验证登录")
    @RequestMapping(value = "validate", method = RequestMethod.POST)

    public ResponseEntity<Message<UserDto>> validate(@RequestBody userValidateDto dto) {
        try {

            logger.info("++++++++++++++++++validate++++++++++++++++");
            logger.info(dto.getMobile()+"  "+ dto.getValidateCode());
            //如果KEY 过期
//            if(!RedixUtil.exits(mobile))
//            {
//                return ResponseEntity.ok(new Message<userInfo>(false,ReturnEnum.Iphone_Validate_Error.getErrorCode(), ReturnEnum.Iphone_Validate_Error.getErrorDesc(), null));
//            }
            //logger.info("===========================================");

            String redisValue = RedixUtil.getString(dto.getMobile());
            //logger.info(redisValue + "=" + validateCode);

            //获取VALUE,进行验证
            if (dto.getValidateCode().equals(redisValue)) {
                //根据用户号码，进行查询，存在返回信息；不存在创建

                userInfo u = userRestfulService.getUserInfoByMobile(dto.getMobile());
                UserDto userInfo = userRestfulService.getUserInfoById(u.getId());
                return ResponseEntity.ok(new Message<UserDto>(true, 0, null, userInfo));
            }
            return ResponseEntity.ok(new Message<UserDto>(false, ReturnEnum.UseRregister_Error.getErrorCode(), ReturnEnum.UseRregister_Error.getErrorDesc(), null));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<UserDto>(false, ReturnEnum.UseRregister_Error.getErrorCode(), ReturnEnum.UseRregister_Error.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    //@SystemControllerLog(description = "验证码验证登录")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public ResponseEntity<Message<UserDto>> getUserInfo(long userId) {
        try {

            UserDto userInfo = userRestfulService.getUserInfoById(userId);
            return ResponseEntity.ok(new Message<UserDto>(true, 0, null, userInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<UserDto>(false, ReturnEnum.UerInfo_ERROR.getErrorCode(), ReturnEnum.UerInfo_ERROR.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }


    /**
     * 获取用户已完成订单
     *
     * @param userId
     * @return
     */
    //@SystemControllerLog(description = "获取用户已完成订单")
    @RequestMapping(value = "getSuccessOrder", method = RequestMethod.GET)
    public ResponseEntity<Message<List<VehicleOrderDto>>> getOrderPaySuccess(@RequestParam("userId") long userId) {
        try {
            List<VehicleOrderDto> list = bicycleRestfulService.getOrderPaySuccess(userId);
            return ResponseEntity.ok(new Message<List<VehicleOrderDto>>(true, 0, null, list));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<List<VehicleOrderDto>>(false, ReturnEnum.Order_Eroor.getErrorCode(), ReturnEnum.Order_Eroor.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 获取用户已完成最后订单
     *
     * @param userId
     * @return
     */
    //@SystemControllerLog(description = "获取用户已完成订单")
    @RequestMapping(value = "getLastSuccessOrder", method = RequestMethod.GET)
    public ResponseEntity<Message<VehicleOrderDto>> getLastSuccessOrder(@RequestParam("userId") long userId) {
        try {
            VehicleOrderDto dto = bicycleRestfulService.getLastSuccessOrder(userId);
            return ResponseEntity.ok(new Message<VehicleOrderDto>(true, 0, null, dto));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<VehicleOrderDto>(false, ReturnEnum.Order_Eroor.getErrorCode(), ReturnEnum.Order_Eroor.getErrorDesc() + "-" + e.getMessage(), null));
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

            if (dto.getVehicleOrderDto() != null && dto.getInfo() != null) {
                if (dto.getVehicleOrderDto().getStatus() == 1) {
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 0, null, dto));
                } else {
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 4, null, dto));
                }

            } else if (dto.getVehicleOrderDto() != null && dto.getInfo() == null) {
                if (dto.getVehicleOrderDto().getStatus() == 1) {
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 0, null, dto));
                } else {
                    return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 4, null, dto));
                }
            } else if (dto.getVehicleOrderDto() == null && dto.getInfo() != null) {
                return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 1, null, dto));
            } else {
                return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(true, 2, null, null));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new Message<VehicleOrderSubscribeDto>(false, 3, e.getMessage(), null));
        }
    }


    /**
     * 上传用户头像图片
     * @param userId 用户ID
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "uploadUserHeadImg")
    public ResponseEntity<Message<String>> uploadUserHeadImg(@RequestParam("userId") long userId,HttpServletRequest request) {
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
                    String imageName = OSSClientUtil.uploadUserImg(file.getInputStream());
                    userInfo user = new userInfo();
                    user.setId(userId);
                    user.setUserImg(imageName);
                    userRestfulService.updateUserInfo(user);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, imageName));
                }
            }
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), null, "请上传文件！"));
        }
        catch (Exception e)
        {
            logger.error("上传用户头像图片报错：",e);
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), ReturnEnum.UpdateUer_ERROR.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 上传用户身份证图片
     * @param userId 用户ID
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "uploadIDCardImg")
    public ResponseEntity<Message<String>> uploadIDCardImg(@RequestParam("userId") long userId,HttpServletRequest request) {
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
                    String imageName = OSSClientUtil.uploadUserImg(file.getInputStream());
                    userInfo user = new userInfo();
                    user.setId(userId);
                    //用户身份证图片
                    user.setIdentityCardphoto(imageName);
                    user.setAuthenStatus(1);
                    userRestfulService.updateUserInfo(user);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, imageName));
                }
            }
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), null, "请上传文件！"));
        }
        catch (Exception e)
        {
            logger.error("上传用户身份证图片报错：",e);
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), ReturnEnum.UpdateUer_ERROR.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 上传用户和身份证合影
     * @param userId 用户ID
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "uploadUserImg")
    public ResponseEntity<Message<String>> uploadIDCardImg2(@RequestParam("userId") long userId,HttpServletRequest request) {
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
                    String imageName = OSSClientUtil.uploadUserImg(file.getInputStream());
                    userInfo user = new userInfo();
                    user.setId(userId);
                    user.setAuthenStatus(1);

                    //用户和身份证合影
                    user.setPhoto(imageName);
                    userRestfulService.updateUserInfo(user);
                    return ResponseEntity.ok(new Message<String>(true, 0, null, imageName));
                }

            }
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), null, "请上传文件！"));
        }
        catch (Exception e)
        {
            logger.error("上传用户和身份证合影报错：",e);
            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(), ReturnEnum.UpdateUer_ERROR.getErrorDesc() + "-" + e.getMessage(), null));
        }
    }

    /**
     * 分享
     * @param userId 用户ID
     * @return
     */
    @RequestMapping(value = "share")
     public ResponseEntity<Message<String>> share(@RequestParam("userId") long userId) {
        return ResponseEntity.ok(new Message<String>(true, 0, null, "http://h5.joybike.com.cn/forward/H5/joy_bike/mytripdetail.html?userId="+userId));
    }


    @RequestMapping(value = "upload")
    public ResponseEntity<Message<String>> upload(@RequestBody FileUploadDataDto dto) {
        logger.info("==================upload===============");
        logger.info(dto);
        String imageName = OSSClientUtil.uploadUserImg(dto.getFile());
        return ResponseEntity.ok(new Message<String>(true, 0, null, imageName));
    }
}
