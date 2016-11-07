package pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayPlatformOpenidGetRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayPlatformOpenidGetResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
import com.joybike.server.api.dto.AlipayDto;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.service.PayRestfulService;
import com.joybike.server.api.util.ToHashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by lishaoyong on 16/11/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class AliPay {


    @Autowired
    AliPayConstructUrlInter aliPayConstructUrlInter;

    @Autowired
    PayRestfulService payRestfulService;


    @Test
    public static void main(String[] args) throws AlipayApiException {

        String serverUrl = "https://openapi.alipay.com/gateway.do";

        String appId = "2088521096580226";

        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALEjhVas+4WJWzL/a3lg6dCIstR1Qs2/RipWGj1MMg0imqUnIJWiK3uf35JBPHVDSAykiWZbw7gjsARo+Q75UhMt+obwiG66CNxMaB5fbmGuYgu0FaykRlYOd0H4ct4B8HiGwN/Q0Yp8J10K3CAS54EqI7r8mfnfKnUtqu39VR4bAgMBAAECgYAtHHCunCsJ3Ose17FVHfstokJ4nMxAfX+u9HKGPctZUQC1InvH357XQuW652YwLsxAe/6J1MDJOj1vQhR7Xb8qulmrP/dOyKQmIhMhByDd2RwT6WifBRqCJeRJgvTPXjUu0S207iU7JXXGuQhL36zCkFol6pXfjlfwuX0cuvkXcQJBAOkB2A08hWQ8SCcQss8WKt9N/jo26ucLe6jqXAhbMll4WD/G4kAoC3B22KpxgxZ93eIbhvn4VT5PxOOo44l1OwUCQQDCnlhqNKWSAP5wPEjlE4NhElGt1DKDIoHLk1l6r7tRhaORyjGoMF0LfcZCLxX6CnF5t8tj6V0lwUWKrX3T5X6fAkASSQHCcChrqEtlPWs60xuEMKOtv9eJUB5hMBsO0NFPsiECyEHDFSForvrrzUKjRjdeYhiijzlpTWs/Dmbkx51dAkAdi2ZGsTlVYds+dJhoVj8ClIJbzjPg3nMv0W2rB1R7ersrHbPOIZFudiiL0ZQXglBZtwIkZ7/hRGbnN5E7gsJnAkA0ezah9rEmPK6JASsUZxQHxhJV4N1lLVM0u9tKwr3hEpUMgNvtIqTmNSPErzJ9Qn8CEMiEDTCX";

        AlipayClient client = new DefaultAlipayClient(serverUrl, appId, privateKey, "json");

        AlipayPlatformOpenidGetRequest req = new AlipayPlatformOpenidGetRequest();

        req.setBizContent("{\"user_ids\":[\"2088521096580226\",\"2088521096580226\",\"2088521096580226\"]}");



        AlipayPlatformOpenidGetResponse res = client.execute(req, null);

        System.out.println(res.getMsg());

        System.out.println(res.getParams());

        System.out.println(res.getBody());
    }


    @Test
    public void bTest(){

//        AlipayDto dto = new AlipayDto();
//
//        dto.setAppenv("1");
//        dto.setBody("单车充值");
//        dto.setIt_b_pay("30m");
//        dto.setOut_trade_no("2016091090");
//        dto.setTotal_fee("100");
//        dto.setSubject("充值100");
//
//        HashMap<String,String> map = ToHashMap.beanToMap(dto);
//
////        map.forEach(new BiConsumer() {
////            @Override
////            public void accept(Object o, Object o2) {
////                System.out.println(o+":" + o2);
////            }
////        });

        ThirdPayBean bean = new  ThirdPayBean();
        bean.setChannelId(2);
        bean.setCreateTime(Date.from(Instant.now()));
        bean.setRecordTime(Date.from(Instant.now()));

        try {
            String a = payRestfulService.payBeanToAliPay(bean,1);
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
