package restfulUnitTest;

import com.alibaba.fastjson.JSON;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dto.UserDto;
import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.model.Message;
import com.joybike.server.api.model.vehicle;
import com.joybike.server.api.model.vehicleHeartbeat;
import com.joybike.server.api.restful.BicycleRestfulApi;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.thirdparty.aliyun.pushHelper;
import com.joybike.server.api.thirdparty.amap.AMapUtil;
import com.joybike.server.api.util.UnixTimeUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by 58 on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class BicycleRestfulApiUnitTest {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private OrderRestfulService orderRestfulService;

    private final Logger logger = Logger.getLogger(BicycleRestfulApi.class);

    @Test
    public void fun()
    {
        vehicle vehicle = null;
        try {
            vehicle = vehicleDao.getVehicleBylockId("200000010");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserPayIngDto dto = null;
        try {
            dto = orderRestfulService.userPayOrder(vehicle.getVehicleId(), UnixTimeUtils.now(), Double.valueOf("40.043"), Double.valueOf("40.043"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //消息推送
        logger.info("--------------------消息推送--------------------");
        logger.info(dto);
        logger.info("--------------------消息推送--------------------");
        logger.info(JSON.toJSON(dto));
        System.out.println(JSON.toJSON(dto));

        Message<UserPayIngDto> message = new Message<>();
        message.setData(dto);
        message.setIsSuccess(true);
        message.setErrorCode(0);
        logger.info("--------------------消息推送--------------------");
        System.out.println(JSON.toJSON(message));
    }

    @Test
    public void test()
    {
        String address= AMapUtil.getAddress("40.043,40.043");
        System.out.println(address);
    }

    @Test
    public void lockTest()
    {
        String param="a1e4a729e9cd4c9fafa35c536108703e;200000012,01,01,1,700103091343,4530,57710,,,2,0,1,0,63,";

        System.out.println(param);

        try {
            String token = param.split(";")[0];
            String content = param.split(";")[1];
            String[] values = content.split(",");

            vehicleHeartbeat heartbeat = new vehicleHeartbeat();

            heartbeat.setLockId(Long.valueOf(values[0]));
            heartbeat.setFirmwareVersion(values[1]);
            heartbeat.setAllocation(values[2]);
            heartbeat.setBaseStationType(values[3]);
            if (values[3].equals("0")) {
                heartbeat.setGpsTime(Long.valueOf(values[4]));
                heartbeat.setDimension(BigDecimal.valueOf(Double.valueOf(values[5])));
                heartbeat.setLongitude(BigDecimal.valueOf(Double.valueOf(values[6])));
            }
            if (values[3].equals("1")) {
                heartbeat.setLockTime(Long.valueOf(values[4]));
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
            heartbeat.setCreateAt(UnixTimeUtils.now());
            //vehicleHeartbeatDao.save(heartbeat);
            System.out.println(heartbeat);

            //车辆锁车
            if(Integer.valueOf(values[11])==0)
            {
                vehicle vehicle = vehicleDao.getVehicleBylockId(heartbeat.getLockId().toString());
                //UserPayIngDto dto = orderRestfulService.userPayOrder(vehicle.getVehicleId(), UnixTimeUtils.now(), Double.valueOf(heartbeat.getLongitude().toString()), Double.valueOf(heartbeat.getDimension().toString()));
                System.out.println(vehicle);
            }
        } catch (Exception e) {
            logger.error("车锁GPS,每隔15秒上报数据发生异常：" + e.getMessage(), e);
        }
    }

    @Autowired
    private UserRestfulService userRestfulService;

    @Test
    public void tesss()
    {
//        try {
//            pushHelper.testPushMessageToIOS("{\"data\":{\"restType\":1,\"vehicleOrderDto\":{\"amount\":0.1,\"beforePrice\":0.00,\"beginAt\":1479629472,\"cyclingTime\":1782,\"beginDimension\":40.044,\"startAddress\":\"北京市海淀区上地街道尚东·数字山谷A区2号楼中关村软件园(软件园三号路)\",\"endAt\":1479631254,\"userId\":29,\"endDimension\":40.043,\"afterPrice\":0.00,\"beginLongitude\":116.289,\"endLongitude\":40.043,\"orderCode\":\"2147948356798\",\"id\":80,\"payId\":13,\"vehicleId\":\"10780121494\",\"status\":15}},\"success\":true,\"errorCode\":0}","37d25e14bc724c21bbb03179fb02dea1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            UserDto userDto=userRestfulService.getUserInfoById(29);
            System.out.println(userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
