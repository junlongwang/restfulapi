import com.joybike.server.api.model.WxNotifyOrder;
import com.alibaba.fastjson.JSON;
import com.joybike.server.api.model.userInfo;
import com.joybike.server.api.util.XStreamUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import myTest.Amortize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 58 on 2016/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class xstreamUnitTest {

    @Test
    public void toXmltest()
    {
        Book book = new Book("gjf","gengjf","23.3");
        String xml = XStreamUtils.toXml(book);
        System.out.println(xml);
    }


    @Test
    public void toObject()
    {
        String xml = "<xml>\n" +
                "\t<appid><![CDATA[wxbabc4e15389aff36]]></appid>\n" +
                "\t<attach><![CDATA[null]]></attach>\n" +
                "\t<bank_type><![CDATA[CFT]]></bank_type>\n" +
                "\t<cash_fee><![CDATA[1]]></cash_fee>\n" +
                "\t<fee_type><![CDATA[CNY]]></fee_type>\n" +
                "\t<is_subscribe><![CDATA[N]]></is_subscribe>\n" +
                "\t<mch_id><![CDATA[1404387302]]></mch_id>\n" +
                "\t<nonce_str><![CDATA[1be883eec3231f9fe43c35bd1b4b3bb5]]></nonce_str>\n" +
                "\t<openid><![CDATA[ouS_kw9fYUdmQKqDxcr1CYRYvQEc]]></openid>\n" +
                "\t<out_trade_no><![CDATA[151]]></out_trade_no>\n" +
                "\t<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "\t<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "\t<sign><![CDATA[658AA2D0A6C2BD0CDF947F735CECE596]]></sign>\n" +
                "\t<time_end><![CDATA[20161031214031]]></time_end>\n" +
                "\t<total_fee>1</total_fee>\n" +
                "\t<trade_type><![CDATA[APP]]></trade_type>\n" +
                "\t<transaction_id><![CDATA[4001552001201610318304560908]]></transaction_id>\n" +
                "</xml>";
        WxNotifyOrder wxNotifyOrder = XStreamUtils.toBean(xml, WxNotifyOrder.class);
        System.out.println(wxNotifyOrder);
    }

    RestTemplate restTemplate = new RestTemplate();
    @Test
    public void postMethodTest_58()
    {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        String file = "D:\\Tencent\\QQ\\Users\\744360792\\FileRecv\\58摊销异常数据.txt";
        Amortize amortize = new Amortize();


        String json= JSON.toJSONString(amortize);
        System.out.println(json);
        HttpEntity<Amortize> formEntity = new HttpEntity<Amortize>(amortize,headers);
        String result = restTemplate.postForObject("http://amortize.web.58dns.org/consume", formEntity, String.class);
        System.out.println(result);
    }



}
