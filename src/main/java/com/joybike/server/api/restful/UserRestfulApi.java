package com.joybike.server.api.restful;

import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.SMSHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
     * @param user
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<Message<userInfo>> update(@RequestBody userInfo user) {
        try {
            userRestfulService.updateUserInfo(user);
            userInfo userInfo = userRestfulService.getUserInfoByMobile(user.getIphone());
            return ResponseEntity.ok(new Message<userInfo>(true, null, userInfo));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<userInfo>(false, "1001：" + "更新用户信息失败", null));
        }

    }

    /**
     * 获取手机验证码并登录
     *
     * @param mobile 手机号码
     * @return
     */
    @RequestMapping(value = "getValidateCode", method = RequestMethod.GET)
    public ResponseEntity<Message<LoginData>> getValidateCode(@RequestParam("mobile") String mobile) {
        int randNo = 0;
        try {
            randNo = new Random().nextInt(9999 - 1000 + 1) + 1000;
            //根据用户号码，进行查询，存在返回信息；不存在创建
            userInfo userInfo = userRestfulService.getUserInfoByMobile(mobile);
            LoginData loginData = new LoginData(String.valueOf(randNo), userInfo);
            //发送短信接口
            SMSHelper.sendValidateCode(mobile, String.valueOf(randNo));
            return ResponseEntity.ok(new Message<LoginData>(true, null, loginData));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<LoginData>(false, "1001：" + e.getMessage(), null));
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
            return ResponseEntity.ok(new Message<Double>(true, null, acountMoney));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<Double>(false, "1001：" + "获取余额信息失败", null));
        }
    }

    /**
     * 获取系统推送信息
     *
     * @return
     */
    @RequestMapping(value = "getMessages", method = RequestMethod.GET)
    public ResponseEntity<Message<List<SysMessage>>> getMessages() {
        return ResponseEntity.ok(new Message<List<SysMessage>>(true, null, new ArrayList<SysMessage>()));
    }
}
