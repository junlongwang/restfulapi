package com.joybike.server.api;

import com.joybike.server.api.dao.UserDao;
import com.joybike.server.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试DEMO
 * Created by liyd on 2015-8-6.
 */
@RestController
public class DemoRestController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("say/{name}")
    public Message say(@PathVariable String name) {

        Message message = new Message();
        message.setName(name);
        message.setText("hello," + name+" "+userDao.findById(1L));

        userDao.test();
        return message;
    }
    @RequestMapping(value = "sayhi",method = RequestMethod.POST)
    public Message sayhi(@RequestParam("name") String name) {

        Message message = new Message();
        message.setName(name);
        message.setText("hello," + name + " " + userDao.findById(1L));
        return message;
    }

    @RequestMapping(value = "user/add",method = RequestMethod.POST)
    public Message addUser(@RequestBody User user) {

        Message message = new Message();
        message.setName(user.getName());
        message.setText("hello," + user.toString());
        return message;
    }
}
