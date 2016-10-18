package com.joybike.server.api.restful;

import com.joybike.server.api.model.Message;
import com.joybike.server.api.model.MyUser;
import com.joybike.server.api.model.SysMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 58 on 2016/10/16.
 */
//"/api/user"
@RestController()
public class UserRestfulApi {

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public ResponseEntity<MyUser> add(@RequestBody MyUser user)
    {
        return ResponseEntity.ok(new MyUser());
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public ResponseEntity<MyUser> update(@RequestBody MyUser user)
    {
        return ResponseEntity.ok(new MyUser());
    }

    /**
     * 获取手机验证码
     * @param mobile 手机号码
     * @param machineUUID 机型唯一编码
     * @return
     */
    @RequestMapping(value = "getValidateCode",method = RequestMethod.GET)
    public ResponseEntity<Message<String>> getValidateCode(@RequestParam("mobile") String mobile,@RequestParam("uuid") String machineUUID)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"1234"));
    }

    /**
     * 获取系统推送信息
     * @return
     */
    @RequestMapping(value = "getMessages",method = RequestMethod.GET)
    public ResponseEntity<Message<List<SysMessage>>> getMessages() {
        return ResponseEntity.ok(new Message<List<SysMessage>>(true, null, new ArrayList<SysMessage>()));
    }
}
