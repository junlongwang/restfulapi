package com.joybike.server.api.restful;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.dto.userInfoDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.SMSHelper;
import com.joybike.server.api.thirdparty.SMSResponse;
import com.joybike.server.api.thirdparty.aliyun.oss.OSSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 58 on 2016/10/16.
 */
@RequestMapping("/user")
@RestController()
public class UserRestfulApi {


    @Autowired
    private UserRestfulService userRestfulService;


    /**
     * 更新用户信息
     *
     * @param userInfoDto
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<Message<userInfo>> update(@RequestBody userInfoDto userInfoDto) {
        try {
            userInfo user = new userInfo();
            user.setId(userInfoDto.getUserId());
            user.setIphone(userInfoDto.getIphone());
            user.setIdNumber(userInfoDto.getIdNumber());
            user.setRealName(userInfoDto.getRealName());
            user.setNationality(userInfoDto.getNationality());
            if(userInfoDto.getIdentityCardphoto()!=null)
            {
                String fileName=OSSClientUtil.uploadUserImg(userInfoDto.getIdentityCardphoto());
                user.setIdentityCardphoto(fileName);
            }
            if(userInfoDto.getPhoto()!=null)
            {
                String fileName=OSSClientUtil.uploadUserImg(userInfoDto.getPhoto());
                user.setPhoto(fileName);
            }
            if(userInfoDto.getUserImg()!=null)
            {
                String fileName=OSSClientUtil.uploadUserImg(userInfoDto.getUserImg());
                user.setUserImg(fileName);
            }
            userRestfulService.updateUserInfo(user);
            userInfo userInfo = userRestfulService.getUserInfoByMobile(user.getIphone());
            return ResponseEntity.ok(new Message<userInfo>(true,0, null, userInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<userInfo>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(),ReturnEnum.UpdateUer_ERROR.getErrorDesc()+"-"+e.getMessage(), null));
        }
    }

    /**
     * 获取手机验证码并登录
     *
     * @param mobile 手机号码
     * @return
     */
    @RequestMapping(value = "getValidateCode", method = RequestMethod.POST)
    public ResponseEntity<Message<LoginData>> getValidateCode(@RequestParam("mobile") String mobile) {
        int randNo = 0;
        try {
            randNo = new Random().nextInt(9999 - 1000 + 1) + 1000;

            //发送短信接口
            SMSResponse smsResponse = SMSHelper.sendValidateCode(mobile, String.valueOf(randNo));
            if (!smsResponse.getErrorCode().equals("0")){
                return ResponseEntity.ok(new Message<LoginData>(false, ReturnEnum.Iphone_Error.getErrorCode(),ReturnEnum.Iphone_Error.getErrorDesc(), null));
            }else{
                //根据用户号码，进行查询，存在返回信息；不存在创建
                userInfo userInfo = userRestfulService.getUserInfoByMobile(mobile);
                LoginData loginData = new LoginData(String.valueOf(randNo), userInfo);
                return ResponseEntity.ok(new Message<LoginData>(true, 0,null, loginData));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<LoginData>(false, ReturnEnum.UseRregister_Error.getErrorCode(),ReturnEnum.UseRregister_Error.getErrorDesc()+"-"+e.getMessage(), null));
        }
    }

    /**
     * 获取用户账户余额
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "getAcountMoney", method = RequestMethod.GET)
    public ResponseEntity<Message<Double>> getAcountMoney(@RequestParam("userid") long userid) {
        try {
            double acountMoney = userRestfulService.getUserAcountMoneyByuserId(userid);
            return ResponseEntity.ok(new Message<Double>(true, 0,null, acountMoney));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<Double>(false, ReturnEnum.Acount_Error.getErrorCode(),ReturnEnum.Acount_Error.getErrorDesc()+"-"+e.getMessage(), null));
        }
    }

    /**
     * 获取系统推送信息
     *
     * @return
     */
    @RequestMapping(value = "getMessages", method = RequestMethod.GET)
    public ResponseEntity<Message<List<SysMessage>>> getMessages() {
        return ResponseEntity.ok(new Message<List<SysMessage>>(true,0, null, new ArrayList<SysMessage>()));
    }
}
