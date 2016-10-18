package com.joybike.server.api.restful;

import com.joybike.server.api.model.BadBicycleForm;
import com.joybike.server.api.model.Bicycle;
import com.joybike.server.api.model.GPSData;
import com.joybike.server.api.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 58 on 2016/10/16.
 */
//@RequestMapping("/api/bicycle")
@RestController()
public class BicycleRestfulApi {

    /**
     * 预约车辆
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "subscribe",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> subscribe(@RequestParam("userId") long userId,@RequestParam("bicycleCode") String bicycleCode)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"预约成功！"));
    }


    /**
     * 取消预约车辆
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "cancle",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> cancle(@RequestParam("userId") long userId,@RequestParam("bicycleCode") String bicycleCode)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"取消预约成功！"));
    }

    /**
     * 寻车
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "lookup",method = RequestMethod.GET)
    public ResponseEntity<Message<String>> lookup(@RequestParam("userId") long userId,@RequestParam("bicycleCode") String bicycleCode)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"寻车成功！"));
    }

    /**
     * 获取可用闲置车辆，一公里以内
     * @param longitude
     * @param dimension
     * @return
     */
    @RequestMapping(value = "available",method = RequestMethod.GET)
    public ResponseEntity<Message<List<Bicycle>>> getAvailable(float longitude ,float dimension)
    {
        return ResponseEntity.ok(new Message<List<Bicycle>>(true,null,new ArrayList<Bicycle>()));
    }


    /**
     * 扫描开锁
     * @param userId
     * @param bicycleCode
     * @return
     */
    @RequestMapping(value = "unlock",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> unlock(@RequestParam("userId") long userId,@RequestParam("bicycleCode") String bicycleCode)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"开锁成功！"));
    }

    /**
     * 锁车动作，服务端回调地址
     * @param param
     */
    @RequestMapping(value = "lock",method = RequestMethod.POST)
    public void lockCallBack(@RequestBody  String param) {

    }

    /**
     * 车上手机卡，每隔15秒上报数据，回调地址
     * @param data
     */
    @RequestMapping(value = "send",method = RequestMethod.POST)
    public void send(@RequestBody GPSData data)
    {

    }


    /**
     * 提交故障车辆信息
     * @param form
     * @return
     */
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> submit(@RequestBody BadBicycleForm form)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"提交成功！"));
    }
}
