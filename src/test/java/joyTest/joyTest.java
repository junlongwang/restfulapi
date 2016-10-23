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
    UserInfoService userInfoService;

    @Autowired
    BankDepositOrderService depositOrderService;

    @Autowired
    BankAcountService bankAcountService;

    @Autowired
    SubscribeInfoService subscribeInfoService;

    @Autowired
    VehicleOrderService vehicleOrderService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehicleRepairService  vehicleRepairService;




    @Test
    public void joy() {
        userInfo userInfo = new userInfo();
        userInfo.setId(Long.valueOf(1));
        userInfo.setRealName("龙子渊");
        userInfo.setIdNumber("1234567890");
        userInfo.setNationality("中国");
        int u = userInfoService.updateUserInfo(userInfo);
        System.out.println(u + ":");
    }

    @Test
    public void getUserInfoTest() {

        userInfo u = userInfoService.getUserInfoByMobile("13721766224");
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
        depositOrderService.depositRecharge(order);

    }

    @Test
    public void bankAcountServiceTest() {
        double amount = bankAcountService.getUserAcountMoneyByuserId(2);
        System.out.println(amount + ":余额");
    }

    /**
     * 预约
     */
    @Test
    public void addSubcribTest() {

//        subscribeInfo info = new subscribeInfo();
        try {
            subscribeInfo info = subscribeInfoService.vehicleSubscribe(2, "jy6", 1477205674);
            if (info!= null){
                System.out.println(info);
            }else{
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
    public void ordertest(){

        try {
            vehicleOrderService.addOrder(1,"jy03",1476983501,BigDecimal.valueOf(123.4),BigDecimal.valueOf(123.89));
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
    public void getVehicleListTest(){

        try {
           List<vehicle> list = vehicleService.getVehicleList(40.0276731,116.355149);
            if (list.size() >0 ){
                list.forEach(new Consumer<vehicle>() {
                    @Override
                    public void accept(vehicle vehicle) {
                        System.out.println(vehicle);
                    }
                });
            }else{
                System.out.println("meiyou");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addvehicleRepairTest(){
        vehicleRepair repair = new vehicleRepair();
        repair.setVehicleId("yj04");
        repair.setCause("不能骑了");
        repair.setCreateId(Long.valueOf(12));
        repair.setCreateAt(12345789);
        repair.setDisposeDepict("");
        repair.setDisposeStatus(0);
        try {
           long id = vehicleRepairService.addVehicleRepair(repair);
            System.out.println(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
