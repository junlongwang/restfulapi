package serviceUnitTest;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.bankDepositOrder;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * Created by 58 on 2016/10/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mvc.xml")
public class wxPublicConstrucUnitTest {

//    @Autowired
//    private WxPublicConstructUrlInter wxPublicConstructUrlInter;
@Autowired
private PayRestfulService payRestfulService;
    @Autowired
    private ThirdPayService ThirdPayService;
    @Test
    public void test()
    {
        ThirdPayBean payOrder = new ThirdPayBean();
        payOrder.setPruductDesc("longziyuan");
        payOrder.setId(Long.valueOf("1415651253232327"));
        payOrder.setOrderMoney(BigDecimal.valueOf(0.01));
        payOrder.setOperIP("192.168.0.1");
        payOrder.setOpenid("o6_bmjrPTlm6_2sgVt7hMZOPfL2M");
        payOrder.setChannelId(0);
        ThirdPayService.execute(payOrder);
//        bankDepositOrder order = new bankDepositOrder();
//        long a = 11;
//        order.setUserId(a);
//        order.setCash(BigDecimal.valueOf(123));
//        order.setPayType(0);
//        order.setCreateAt(UnixTimeUtils.now());
//        order.setRechargeType(0);
//        order.setStatus(1);
//        try {
//            long orderId = payRestfulService.depositRecharge(order);
//            System.out.println(orderId);
//            if (orderId >0){
//                payBean.setId(orderId);
//                return ThirdPayService.execute(payBean);
//            }else{
//
//            }
//        } catch (Exception e) {
//            throw new RestfulException(ReturnEnum.Recharge_Error);
//        }
    }
}
