package joyTest;

import com.joybike.server.api.Enum.DepositStatus;
import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.model.bankDepositOrder;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.service.BankAcountService;
import com.joybike.server.api.service.BankDepositOrderService;
import com.joybike.server.api.service.UserInfoService;

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
    public void getUserInfoTest(){

        userInfo u = userInfoService.getUserInfoByMobile("13721766224");
        System.out.println(u.getRealName() + "，的信息");

    }

    @Test
    public void depositOrderTest(){
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
    public void bankAcountServiceTest(){
       double amount = bankAcountService.getUserAcountMoneyByuserId(2);
        System.out.println(amount + ":余额");
    }
}
