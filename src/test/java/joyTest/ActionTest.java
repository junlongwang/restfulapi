package joyTest;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.BicycleRestfulService;
import com.joybike.server.api.service.OrderRestfulService;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

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
        long userId = 3;
        String bicycleCode = "yj05";
        int beginAt = UnixTimeUtils.now();
        try {
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
                System.out.println("有未支付的订单");
            } else {
                bicycleRestfulService.vehicleSubscribe(userId, bicycleCode, beginAt);
                System.out.println("预约成功");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void errorTest(){
        System.out.println(ReturnEnum.ConsumedOrderList_Error.toString());
    }

    @Test
    public void cancleTest(){
        long userId = 2;
        String bicycleCode = "yj03";

        try {
            int a = bicycleRestfulService.deleteSubscribeInfo(userId, bicycleCode);
            if (a >0) System.out.println(ReturnEnum.Cancel_Success.getErrorDesc());
            else System.out.println(ReturnEnum.No_Subscribe.getErrorDesc());

        } catch (Exception e) {
            System.out.println(ReturnEnum.Cancel_Error.toString());
        }
    }

    //40.0276731, 116.355149

    @Test
    public void getAvailableTest(){
        double longitude = 40.0276731;
        double dimension = 116.355149;

        try {
            List<vehicle> list = bicycleRestfulService.getVehicleList(longitude, dimension);
            if (list.size() > 0){
                list.forEach(new Consumer<vehicle>() {
                    @Override
                    public void accept(vehicle vehicle) {
                        System.out.println(vehicle);
                    }
                });
            }else{
                System.out.println(ReturnEnum.No_Vehicle.toString());
            }
        } catch (Exception e) {
            System.out.println("GPS信号丢失");
        }
    }

    @Test
    public void unlockTest(){
        long orderId = 0;
        long userId = 1;
        String bicycleCode = "jy01";
        int beginAt = UnixTimeUtils.now();
        double beginLongitude = 40.0276731;
        double beginDimension = 116.355149;


        try {
            //获取是否有未支付订单
            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(userId);

            if (order != null) {
//                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.NoPay_Error.toString(), null));
                System.out.println(0);

            } else {
                orderId = bicycleRestfulService.unlock(userId, bicycleCode, beginAt, beginLongitude, beginDimension);
            }

            if (orderId > 0) {
//                return ResponseEntity.ok(new Message<String>(true, null, ReturnEnum.Unlock_Success.getErrorDesc()));
                System.out.println(1);
            } else {
                System.out.println(2);
//                return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Unlock_Error.toString(), null));
            }
        } catch (Exception e) {
            System.out.println(e);
//            return ResponseEntity.ok(new Message<String>(false, ReturnEnum.Unlock_Error.toString(), null));
        }
    }

    @Test
    public void submitTest() throws Exception {
        vehicleRepair vehicleRepair = new vehicleRepair();
        vehicleRepair.setVehicleId("yj03");
        long id = bicycleRestfulService.addVehicleRepair(vehicleRepair);
        System.out.println(id);
    }

    @Test
    public void updatetest(){
        userInfo u = new userInfo();
        u.setRealName("lili");
//        u.setIphone("13910991532");
        u.setId(Long.valueOf(1));

        try {
           userRestfulService.updateUserInfo(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            userInfo userInfo = userRestfulService.getUserInfoByMobile(u.getIphone());
            System.out.println(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAcountMoneyTest(){
        long userid = 1;
        try {
            double acountMoney = userRestfulService.getUserAcountMoneyByuserId(userid);
            System.out.println(acountMoney);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getConsumeLogsTest(){
//        try {
//            List<bankConsumedOrder> list = payRestfulService.getBankConsumedOrderList(1);
//            list.forEach(new Consumer<bankConsumedOrder>() {
//                @Override
//                public void accept(bankConsumedOrder bankConsumedOrder) {
//                    System.out.println(bankConsumedOrder);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            List<bankDepositOrder> list = payRestfulService.getBankDepositOrderList(1);
            list.forEach(new Consumer<bankDepositOrder>() {
                @Override
                public void accept(bankDepositOrder depositOrder) {
                    System.out.println(depositOrder);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void productTest(){
        List<product> list = orderRestfulService.getProductList();
        list.forEach(new Consumer<product>() {
            @Override
            public void accept(product product) {
                System.out.println(product);
            }
        });
    }

    @Test
    public void aTest(){

        int time = 1/60/30;
        double t = (1800/60)/30;
        if (t >0){
            time = time + 1;
        }
        System.out.println(BigDecimal.valueOf(time));
    }

    @Test
    public void consumeTest(){
        double dimension = 40.049;
        double longitude = 116.294;

        try {
            List<vehicle> list = bicycleRestfulService.getVehicleList(dimension , longitude);
            if (list.size() > 0){
                list.forEach(new Consumer<vehicle>() {
                    @Override
                    public void accept(vehicle vehicle) {
                        System.out.println(vehicle);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dtoTest(){
        try {
            bicycleRestfulService.getOrderPaySuccess(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
