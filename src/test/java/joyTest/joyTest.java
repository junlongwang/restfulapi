package joyTest;

import com.joybike.server.api.Enum.DepositStatus;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.model.bankDepositOrder;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.model.vehicleOrder;
import com.joybike.server.api.service.*;

import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;


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

        long id = subscribeInfoService.addSubscribeInfo(1, "jy02", 1476983501);
        if (id == 0) System.out.println("该车辆不可预约");
        if (id > 0) System.out.println("预约成功");
    }

    /**
     * 扫码解锁
     */
    @Test
    public void ordertest(){

        vehicleOrderService.addOrder(1,"jy03",1476983501,BigDecimal.valueOf(123.4),BigDecimal.valueOf(123.89));

    }

    /**
     * 根据锁车动作来判断这辆车是谁骑行的
     */
    @Test
    public void payVelicle(){

        vehicleOrder order = vehicleOrderService.getOrderByVehicleId("jy04");
        System.out.println(order);
    }
}
