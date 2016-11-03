//package pay;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.request.AlipayTradePayRequest;
//import com.alipay.api.response.AlipayTradePayResponse;
//import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
//import com.joybike.server.api.dto.AlipayDto;
//import com.joybike.server.api.model.RedirectParam;
//import com.joybike.server.api.model.ThirdPayBean;
//import com.joybike.server.api.service.PayRestfulService;
//import com.joybike.server.api.util.ToHashMap;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.time.Instant;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.BiConsumer;
//
///**
// * Created by lishaoyong on 16/11/1.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
//public class AliPay {
//
//
//    @Autowired
//    AliPayConstructUrlInter aliPayConstructUrlInter;
//
//    @Autowired
//    PayRestfulService payRestfulService;
//
//
//    @Test
//    public  void aTest() throws AlipayApiException {
//        AlipayClient alipayClient = new DefaultAlipayClient("http://openapi.alipay.com/gateway.do","2016102202289143",
//                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL+V3cjrWGPzv0tx6PuU5QuFtNKOh3z2uefh3/mkMk5Ynf5u6ideNTm+wig9gZ+zIwtnb195yESVYEXFI3lKhokl2AGWqkcTLyde9ONR1vuPyMJSwrj5AwoslEg7VAUO7pcoE4t14/4H+jp7wNq7OdDdkp2s1/YcwmxVO7UthrQBAgMBAAECgYADcNSX3CotOV5xI22UvxrR9yfFNEwYoXG7SWI1YI6Ku6qyvBk2dcms7NEd2eTvEk+Jz+S/KTfAi5I8DotRjVYXLoMtaf5J5JZv7YHhTb5s+mwaYv76Zj/xPDS9N8Gdn5Im2J7rAoIqT0LgrLsItxyblCSDIGl+osrI4oc0wwRJMQJBAOCucs7y0KtQ5tl5OSJwREGwYYNMCh2wVSU8eVZdC8PjzKbDJ3wCC9dSD0MHUFst40QpAuX236RGhCZyIZVY5BUCQQDaSmqjPa+ivUwPo4I/HsiFUrXVChA/hGje5KhJ7AQgrZwCWBVddeMW83BBRUwjSxIxpOyS+oZr/rIsl7SbNq89AkEAzg8P2j6VI9lRrrffr3b5Eqm59NmjGXFj1X+t5If3R1O/mF2486FIzNREjPvDNaxTF2rpbpDIsPe/MSdRJ5BbtQJAVnDhiYMD6g3L8OVuzlACV8lI9/PkO0LTxHAHtD4h5E7bWJJI45mrM4tzCGXIoLE5oy8L/6f1Uw6ov7/TZSNigQJAcUpgecFc5STQvpXYch9ptufqu+96GhR6pQDgkXUXnSWrNxmyNPFPoioip9nOV/symU0Du65r+kNy/lexVT94wA==",
//                "json","GBK","MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/ld3I61hj879Lcej7lOULhbTSjod89rnn4d/5pDJOWJ3+buonXjU5vsIoPYGfsyMLZ29fechElWBFxSN5SoaJJdgBlqpHEy8nXvTjUdb7j8jCUsK4+QMKLJRIO1QFDu6XKBOLdeP+B/o6e8DauznQ3ZKdrNf2HMJsVTu1LYa0AQIDAQAB");
//        AlipayTradePayRequest request = new AlipayTradePayRequest();
//        request.setBizContent("{" +
//                "    \"out_trade_no\":\"20150320010101001\","
//                        +
//                        "    \"scene\":\"bar_code,wave_code\","
//                        +
//                        "    \"auth_code\":\"28763443825664394\","
//                        +
//                        "    \"subject\":\"Iphone6 16G\","
//                        +
//                        "    \"timeout_express\":\"90m\","
//                        +
//                        "  }");
//        AlipayTradePayResponse response = alipayClient.execute(request);
//        if(response.isSuccess()){
//            System.out.println("调用成功");
//        } else {
//            System.out.println("调用失败");
//        }
//    }
//
//    @Test
//    public void bTest(){
//
////        AlipayDto dto = new AlipayDto();
////
////        dto.setAppenv("1");
////        dto.setBody("单车充值");
////        dto.setIt_b_pay("30m");
////        dto.setOut_trade_no("2016091090");
////        dto.setTotal_fee("100");
////        dto.setSubject("充值100");
////
////        HashMap<String,String> map = ToHashMap.beanToMap(dto);
////
//////        map.forEach(new BiConsumer() {
//////            @Override
//////            public void accept(Object o, Object o2) {
//////                System.out.println(o+":" + o2);
//////            }
//////        });
//
//        ThirdPayBean bean = new  ThirdPayBean();
//        bean.setChannelId(2);
//        bean.setCreateTime(Date.from(Instant.now()));
//        bean.setRecordTime(Date.from(Instant.now()));
//
//        try {
//            String a = payRestfulService.payBeanToAliPay(bean,1);
//            System.out.println(a);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//}
