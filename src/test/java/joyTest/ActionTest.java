package joyTest;

import com.joybike.server.api.model.Message;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lishaoyong on 16/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class ActionTest {


    @Autowired
    PayRestfulService payRestfulService;
    @Autowired
    UserRestfulService userRestfulService;
    @Autowired
    OrderRestfulService orderRestfulService;
    @Autowired
    BicycleRestfulService bicycleRestfulService;

    @Test
    public void subscribeTest(){
        long userId = 1;
        String bicycleCode = "yj03";
        int beginAt = 1477314893;
        try {
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
                System.out.println("有未支付的订单");
            } else {
                bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                System.out.println("预约成功");
            }
        } catch (Exception e) {
            System.out.println("错误"+ e.getMessage());
        }
    }
}
