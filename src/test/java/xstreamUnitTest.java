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
        String xml = "<book price=\"23.3\">\n" +
                "  <name>gjf</name>\n" +
                "  <author>gengjf</author>\n" +
                "</book>";
        Book book= XStreamUtils.toBean(xml, Book.class);
        System.out.println(book);
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
