package daoUnitTest;

import com.alibaba.fastjson.JSON;
import com.joybike.server.api.dao.UserCouponDao;
import com.joybike.server.api.dao.smsDao;
import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Created by lishaoyong on 16/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class userCouponTest {

    @Autowired
    UserCouponDao userCouponDao;

    @Autowired
    smsDao smsDao;

    @Test
    public void insertTest(){
        userCoupon userCoupon = new userCoupon(Long.valueOf(3),Long.valueOf(1), UnixTimeUtils.now(),0,UnixTimeUtils.now());
        long id = userCouponDao.save(userCoupon);
        System.out.println(id + "：");
    }

    @Test
    public void updateTest(){
        Map map = new HashMap();
        map.put("status",1);
        map.put("userId",1);
        map.put("couponId", 1);
        long i = 0;
        try {
            i = userCouponDao.updateCoupon(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(i + ":");
    }

    @Test
    public void getTest(){
        List<userCoupon> list = null;
        try {
            list = userCouponDao.getValidList(1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(list.size());

    }

    @Test
    public void getCountTest(){
        int vaCount = 0;
        try {
            vaCount = userCouponDao.getValidCount(1, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(vaCount + "个");
    }

    @Test
    public void test()
    {
        List<sms> list=smsDao.getSmsMessages(11);
        System.out.println(list);
    }

    @Test
    public void fun()
    {
        Message<UserPayIngDto> message = new Message<>();

        UserPayIngDto dto = new UserPayIngDto();
        dto.setRestType(1);

        //    public VehicleOrderDto(Long id, String orderCode, Long userId,
        // BigDecimal beforePrice, BigDecimal afterPrice, Long payId, Integer status,
        // String vehicleId, Integer beginAt, Integer endAt, BigDecimal beginDimension,
        // BigDecimal beginLongitude, BigDecimal endDimension, BigDecimal endLongitude, Integer cyclingTime, BigDecimal tripDist, List<vehicleHeartbeat> vehicleHeartbeatList,
        // String startAddress, String endAddress, String cyclingImg, BigDecimal amount)
        VehicleOrderDto vehicleOrderDto = new VehicleOrderDto(1L,"骑行订单编码",11L,
                BigDecimal.ONE,BigDecimal.ONE,111l,1,"车辆编码",
                1489078110,148907812,BigDecimal.valueOf(130.34),BigDecimal.valueOf(150.44),BigDecimal.valueOf(290.33),BigDecimal.valueOf(368.66),3600,BigDecimal.valueOf(2300.45),new ArrayList<>()
                ,"天安门","东直门","",BigDecimal.valueOf(290.00));

        dto.setVehicleOrderDto(vehicleOrderDto);

        message.setData(dto);
        message.setIsSuccess(true);
        message.setErrorCode(0);
        System.out.println(JSON.toJSON(message));
    }
}
