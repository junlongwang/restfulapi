package com.joybike.server.api.restful;

import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.LoginData;
import com.joybike.server.api.model.*;
import com.joybike.server.api.thirdparty.VehicleComHelper;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * Created by 58 on 2016/10/16.
 */
//@RequestMapping("/api/bicycle")
@RestController()
public class BicycleRestfulApi {

    static {
        //DOMConfigurator.configure("/opt/soft/log4j.xml");
    }

    @Autowired
    private VehicleHeartbeatDao vehicleHeartbeatDao;

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
        VehicleComHelper.find(bicycleCode);
        return ResponseEntity.ok(new Message<String>(true,null,"寻车成功！"));
    }

    /**
     * 获取可用闲置车辆，一公里以内
     * @param longitude
     * @param dimension
     * @return
     */
    @RequestMapping(value = "available",method = RequestMethod.GET)
    public ResponseEntity<Message<List<vehicle>>> getAvailable(float longitude ,float dimension)
    {
        return ResponseEntity.ok(new Message<List<vehicle>>(true,null,new ArrayList<vehicle>()));
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
        VehicleComHelper.openLock(bicycleCode);
        return ResponseEntity.ok(new Message<String>(true,null,"开锁成功！"));
    }

    /**
     * 锁车动作，服务端回调地址
     * @param param
     */
    @RequestMapping(value = "lockCall",method = RequestMethod.POST)
    public void lockCallBack(@RequestBody  String param) {

        Logger logger =Logger.getLogger(BicycleRestfulApi.class);
        logger.info(param);


        String token=param.split(";")[0];

        String content=param.split(";")[1];
        String[] values=content.split(",");

        vehicleHeartbeat heartbeat = new vehicleHeartbeat();

        heartbeat.setLockId(Long.valueOf(values[0]));
        heartbeat.setFirmwareVersion(values[1]);
        heartbeat.setAllocation(values[2]);
        heartbeat.setBaseStationType(values[3]);
        if(values[3]=="0")
        {
            heartbeat.setGpsTime(Integer.valueOf(values[4]));
            heartbeat.setDimension(BigDecimal.valueOf(Double.valueOf(values[5])));
            heartbeat.setLongitude(BigDecimal.valueOf(Double.valueOf(values[6])));
        }
        if(values[3]=="1")
        {
            heartbeat.setLockTime(Integer.valueOf(values[4]));
            heartbeat.setCellId(values[5]);
            heartbeat.setStationId(values[6]);
        }

        heartbeat.setSpeed(values[7]);
        heartbeat.setDirection(values[8]);
        heartbeat.setArousalType(Integer.valueOf(values[9]));
        heartbeat.setCustom(values[10]);
        heartbeat.setLockStatus(Integer.valueOf(values[11]));
        heartbeat.setBatteryStatus(Integer.valueOf(values[12]));
        heartbeat.setBatteryPercent(values[13]);
        //heartbeat.setCreateAt();
        vehicleHeartbeatDao.save(heartbeat);



    }

    /**
     * 车上手机卡，每隔15秒上报数据，回调地址
     * @param data
     */
    @RequestMapping(value = "send",method = RequestMethod.POST)
    public void send(@RequestBody vehicleHeartbeat data)
    {

    }


    /**
     * 提交故障车辆信息
     * @param form
     * @return
     */
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public ResponseEntity<Message<String>> submit(@RequestBody vehicleRepair form)
    {
        return ResponseEntity.ok(new Message<String>(true,null,"提交成功！"));
    }


    public static void main(String[] args) {
        //DOMConfigurator.configure("E:\\开源项目\\restfulapi\\src\\main\\resources\\log4j.xml");
//        System.out.println("hello");
//
//        Logger logger =Logger.getLogger(BicycleRestfulApi.class);
//        logger.info("gengjinfeng");

        //method 2
        String msg="";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YY-MM-dd HH:mm:ss");
        msg+=sdf.format(date);
        System.out.println(msg);

        //LoginData loginData = new

        //Message<LoginData> message=new Message<LoginData>(true, null, loginData);

    }
}
