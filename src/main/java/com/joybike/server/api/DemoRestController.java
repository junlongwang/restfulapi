package com.joybike.server.api;

import com.joybike.server.api.Infrustructure.SystemControllerLog;
import com.joybike.server.api.dao.UserInfoDao;
import com.joybike.server.api.model.User;
import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * 测试DEMO
 * Created by liyd on 2015-8-6.
 */
@RestController
public class DemoRestController {

    @Value("#{thirdparty}")
    private Properties thirdpartyProperty;

    @Autowired
    private UserInfoDao userInfoDao;

    @SystemControllerLog(description = "sayHelloworld")
    @RequestMapping("say/{name}")
    public Message say(@PathVariable String name) {

        Message message = new Message();
        message.setName(name);
        //message.setText("hello," + name + "code:" + RedixUtil.getString("15110184829"));
        message.setText("hello," + name);
        //userDao.test();
        return message;
    }
    @RequestMapping(value = "sayhi",method = RequestMethod.POST)
    public Message sayhi(@RequestParam("name") String name) {

        Message message = new Message();
        message.setName(name);
        message.setText("hello," + name);
        return message;
    }

    @RequestMapping(value = "user/addUser",method = RequestMethod.POST)
    public Message addUser(@RequestBody User user) {

        Message message = new Message();
        message.setName(user.getName());
        message.setText("hello," + user.toString());
        return message;
    }

    public static void main(String[] args) {


    }
}
