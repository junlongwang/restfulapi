package serviceUnitTest;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.ThirdPayService.impl.WxappConstructUrlImpl;
import com.joybike.server.api.dto.RefundDto;
import com.joybike.server.api.model.*;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.service.UserRestfulService;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import com.joybike.server.api.util.XStreamUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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
    @Autowired
    private UserRestfulService userRestfulService;
    @Test
    public void test()
    {
//        ThirdPayBean payOrder = new ThirdPayBean();
//        payOrder.setPruductDesc("longziyuan");
//        payOrder.setId(Long.valueOf("1411321312"));
//        payOrder.setOrderMoney(BigDecimal.valueOf(0.01));
//        payOrder.setOperIP("10.172.9.68");
//        payOrder.setOpenid("oyPXcwU5B-cZBwKD9KvQLk__bzYc");
//        payOrder.setChannelId(2);
//
//        String a = ThirdPayService.execute(payOrder);
//        System.out.println(a);
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



//        String xml = "<xml>\n" +
//                "\t<appid><![CDATA[wxbabc4e15389aff36]]></appid>\n" +
//                "\t<attach><![CDATA[null]]></attach>\n" +
//                "\t<bank_type><![CDATA[CFT]]></bank_type>\n" +
//                "\t<cash_fee><![CDATA[1]]></cash_fee>\n" +
//                "\t<fee_type><![CDATA[CNY]]></fee_type>\n" +
//                "\t<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
//                "\t<mch_id><![CDATA[1404387302]]></mch_id>\n" +
//                "\t<nonce_str><![CDATA[1be883eec3231f9fe43c35bd1b4b3bb5]]></nonce_str>\n" +
//                "\t<openid><![CDATA[ouS_kw9fYUdmQKqDxcr1CYRYvQEc]]></openid>\n" +
//                "\t<out_trade_no><![CDATA[151]]></out_trade_no>\n" +
//                "\t<result_code><![CDATA[SUCCESS]]></result_code>\n" +
//                "\t<return_code><![CDATA[SUCCESS]]></return_code>\n" +
//                "\t<sign><![CDATA[658AA2D0A6C2BD0CDF947F735CECE596]]></sign>\n" +
//                "\t<time_end><![CDATA[20161031214031]]></time_end>\n" +
//                "\t<total_fee>1</total_fee>\n" +
//                "\t<trade_type><![CDATA[APP]]></trade_type>\n" +
//                "\t<transaction_id><![CDATA[4001552001201610318304560908]]></transaction_id>\n" +
//                "</xml>";
//        WxNotifyOrder wxNotifyOrder = XStreamUtils.toBean(xml, WxNotifyOrder.class);
//        if (wxNotifyOrder.getTransaction_id() != null) {
//            String returncode = ThirdPayService.callBack(wxNotifyOrder);
//            System.out.print(returncode);
//        }
//        RefundDto refundDto = new RefundDto();
//        refundDto.setUserId(Long.valueOf(1));
//        bankDepositOrder order = payRestfulService.getDepositOrderId(refundDto.getUserId());
//        System.out.print(order);
        ThirdPayBean payBean = new ThirdPayBean();
        payBean.setOrderMoney(BigDecimal.valueOf(0.1));
        payBean.setChannelId(1);
        payBean.setTransaction_id("2016111921001004640202377164");
        payBean.setId(Long.valueOf(573));
        payBean.setRefundid(123L);
        //调用第三方支付退款操作
        String result = ThirdPayService.executeRefund(payBean);
        System.out.print(result);

//        userInfo user = new userInfo();
//        user.setId(1L);
//        user.setSecurityStatus(0);
//        int res_upUser = 0;
//        try {
//            res_upUser = userRestfulService.updateUserInfo(user);
//            System.out.print(res_upUser);
//        }catch (Exception e){
//
//        }
    }
}
