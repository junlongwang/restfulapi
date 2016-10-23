package com.joybike.server.api.restful;

import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BankAcountService;
import com.joybike.server.api.service.UserInfoService;
import com.joybike.server.api.thirdparty.SMSHelper;
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
    private UserInfoService userInfoService;

    @Autowired
    private BankAcountService bankAcountService;

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<userInfo> add(@RequestBody userInfo user) {

        return ResponseEntity.ok(new userInfo());
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<userInfo> update(@RequestBody userInfo user) {
        return ResponseEntity.ok(new userInfo());
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
            userInfo userInfo = userInfoService.getUserInfoByMobile(mobile);
            LoginData loginData = new LoginData(String.valueOf(randNo), userInfo);
            return ResponseEntity.ok(new Message<LoginData>(true, null, loginData));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<LoginData>(false, "1001：" + e.getMessage(), null));
        } finally {
            //发送短信接口
            SMSHelper.sendValidateCode(mobile, String.valueOf(randNo));
        }
    }

    /**
     * 获取用户账户余额
     *
     * @param userid
     * @return
     */
    @RequestMapping(value = "getAcountMoney", method = RequestMethod.GET)
    public ResponseEntity<Message<Double>> getAcountMoney(@RequestParam("userid") String userid) {
        try {
            long user_id = Long.valueOf(userid);
            double acountMoney = bankAcountService.getUserAcountMoneyByuserId(user_id);
            return ResponseEntity.ok(new Message<Double>(true, null, acountMoney));
        } catch (Exception e) {
            return ResponseEntity.ok(new Message<Double>(false, "1001：" + e.getMessage(), null));
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
