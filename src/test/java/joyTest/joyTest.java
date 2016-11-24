package joyTest;

import com.alibaba.fastjson.JSON;
import com.joybike.server.api.Enum.*;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.VehicleDao;
import com.joybike.server.api.dto.UserDto;
import com.joybike.server.api.dto.UserPayIngDto;
import com.joybike.server.api.dto.VehicleOrderDto;
import com.joybike.server.api.dto.VehicleOrderSubscribeDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.*;

import com.joybike.server.api.util.MyTaskXml;
import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private SubscribeInfoDao subscribeInfoDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    MyTaskXml myTaskXml;

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
            u = userRestfulService.getUserInfoByMobile("13721761224");
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
    public void getByidTest() {
        try {
            UserDto userInfo = userRestfulService.getUserInfoById(1);
            System.out.println(userInfo);
            System.out.println("1000:::" + JSON.toJSON(ResponseEntity.ok(new Message<userInfo>(false, ReturnEnum.UerInfo_ERROR.getErrorCode(), ReturnEnum.UerInfo_ERROR.getErrorDesc(), null))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aTest() {
        try {
            payRestfulService.updateDepositOrderById(21, PayType.weixin, "weixin", "weixin", 1479546894);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void BTest() {
        try {
//            VehicleOrderDto dto = bicycleRestfulService.getLastSuccessOrder(11);
//            System.out.println(dto);
//            UserDto userInfo = userRestfulService.getUserInfoById(11);

//            System.out.println(userInfo);
//
//            long orderId = 0;
//
//            //获取是否有未支付订单
//            vehicleOrder order = orderRestfulService.getNoPayOrderByUserId(11);
//            VehicleOrderDto dto = new VehicleOrderDto();
//            if (order != null) {
//                System.out.println(1);
//            } else {
//                dto = bicycleRestfulService.unlock(11, "BIKE007", 1478057275, 116.444, 40.076);
//            }
//
//            if (dto != null) {
//                System.out.println(dto);
//            } else {
//                System.out.println(3);
//            }

//            public UserPayIngDto userPayOrder(String bicycleCode, int endAt, double endLongitude, double endDimension) throws Exception,RestfulException {

                orderRestfulService.userPayOrder("BIKE007",1479057275,116.444, 40.076);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void ADtEST() {
//        try {
//            subscribeInfo subscribeInfo = bicycleRestfulService.vehicleSubscribe(101, "10780121495", 1479915886);
//            System.out.println("*****" + subscribeInfo);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }

//        try {
//            vehicleDao.updateVehicleUseStatus("JOY002", UseStatus.free);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        myTaskXml.show();


//        try {
//            List<subscribeInfo> list = subscribeInfoDao.getSqlByTime(UnixTimeUtils.now());
//            list.forEach(new Consumer<subscribeInfo>() {
//                @Override
//                public void accept(subscribeInfo subscribeInfo) {
//                    System.out.println(subscribeInfo);
//                    try {
//                        vehicleDao.updateVehicleUseStatus(subscribeInfo.getVehicleId(), UseStatus.free);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        try {
//            bicycleRestfulService.deleteByExpire();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            vehicleOrder noPayorder = orderRestfulService.getNoPayOrderByUserId(101, OrderStatus.end);
//
//            vehicleOrder useOrder = orderRestfulService.getNoPayOrderByUserId(101, OrderStatus.newly);
//            if (noPayorder != null){
//                System.out.println("有未支付订单");
//            }
//            if (useOrder != null){
//                System.out.println("正在骑行中");
//            }
//            if (noPayorder == null && useOrder == null){
//                bicycleRestfulService.unlock(100,"10780121494",1479912602,116.287,40.043);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}
