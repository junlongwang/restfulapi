package joyTest;

import com.joybike.server.api.Enum.DepositStatus;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.*;

import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;


/**
 * Created by lishaoyong on 16/10/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class joyTest {


    @Autowired
    PayRestfulService payRestfulService;
    @Autowired
    UserRestfulService userRestfulService;
    @Autowired
    OrderRestfulService orderRestfulService;
    @Autowired
    BicycleRestfulService bicycleRestfulService;


    @Test
    public void joy() {
        userInfo userInfo = new userInfo();
        userInfo.setId(Long.valueOf(1));
        userInfo.setRealName("龙子渊");
        userInfo.setIdNumber("1234567890");
        userInfo.setNationality("中国");
        int u = 0;
        try {
            u = userRestfulService.updateUserInfo(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(u + ":");
    }

    @Test
    public void getUserInfoTest() {

        userInfo u = null;
        try {
            u = userRestfulService.getUserInfoByMobile("13721766224");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(u.getRealName() + "，的信息");

    }

    @Test
    public void depositOrderTest() {
        bankDepositOrder order = new bankDepositOrder();
        order.setUserId(Long.valueOf(2));
        order.setCash(BigDecimal.valueOf(1000));
        order.setAward(BigDecimal.valueOf(0));
        order.setStatus(DepositStatus.susuccess.getValue());
        order.setPayType(PayType.weixin.getValue());
        order.setPayAcount("lishaoyong");
        order.setPayDocumentid("lishaoyong");
        order.setMerchantId("lishaoyong");
        order.setPayAt(UnixTimeUtils.now());
        try {
            payRestfulService.depositRecharge(order);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 预约
     */
    @Test
    public void addSubcribTest() {

//        subscribeInfo info = new subscribeInfo();
        try {
            subscribeInfo info = bicycleRestfulService.vehicleSubscribe(2, "jy6", 1477205674);
            if (info != null) {
                System.out.println(info);
            } else {
                System.out.println("失败");
            }
        } catch (Exception e) {
            System.out.println(e.toString() + "信息");
        }
//        if (id == 0) System.out.println("该车辆不可预约");
//        if (id > 0) System.out.println("预约成功");
    }

    /**
     * 扫码解锁
     */
    @Test
    public void ordertest() {

        try {
            orderRestfulService.addOrder(1, "jy04", 1476983501, 123.4, 123.89);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//
//    /**
//     * 根据锁车动作来判断这辆车是谁骑行的
//     */
//    @Test
//    public void payVelicle(){
//
//        vehicleOrder order = vehicleOrderService.getOrderByVehicleId("jy04");
//        System.out.println(order);
//    }

    @Test
    public void getVehicleListTest() {

        try {
            List<vehicle> list = bicycleRestfulService.getVehicleList(40.0276731, 116.355149);
            if (list.size() > 0) {
                list.forEach(new Consumer<vehicle>() {
                    @Override
                    public void accept(vehicle vehicle) {
                        System.out.println(vehicle);
                    }
                });
            } else {
                System.out.println("meiyou");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addvehicleRepairTest() {
        vehicleRepair repair = new vehicleRepair();
        repair.setVehicleId("yj04");
        repair.setCause("不能骑了");
        repair.setCreateId(Long.valueOf(12));
        repair.setCreateAt(12345789);
        repair.setDisposeDepict("");
        repair.setDisposeStatus(0);
        try {
            long id = bicycleRestfulService.addVehicleRepair(repair);
            System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBankDepositOrderListTest() {

        try {
            List<bankDepositOrder> list = payRestfulService.getBankDepositOrderList(1);
            if (list.size() > 0) {
                list.forEach(new Consumer<bankDepositOrder>() {
                    @Override
                    public void accept(bankDepositOrder depositOrder) {
                        System.out.println(depositOrder);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bankConsumedOrderServiceTest() {
        try {
            List<bankConsumedOrder> list = payRestfulService.getBankConsumedOrderList(1);
            if (list.size() > 0) {
                list.forEach(new Consumer<bankConsumedOrder>() {
                    @Override
                    public void accept(bankConsumedOrder bankConsumedOrder) {
                        System.out.println(bankConsumedOrder);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getVehicleHeartbeatListTest() {

        try {
            bicycleRestfulService.getVehicleHeartbeatList("jy02", 1, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByidTest(){
        try {
            userInfo userInfo = userRestfulService.getUserInfoById(1);
            System.out.println(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
