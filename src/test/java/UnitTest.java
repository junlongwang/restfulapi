import com.joybike.server.api.Message;
import com.joybike.server.api.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import java.util.Properties;

/**
 * Created by 58 on 2016/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-mvc.xml")
public class UnitTest {

    RestTemplate restTemplate = new RestTemplate();
    @Test
    public void getMethodTest()
    {
        String content=restTemplate.getForObject("http://localhost:8080/Spring4RestfulDemo/say/{0}", String.class, "XXXLSY");
        System.out.println(content);
    }

    @Test
    public void postMethodTest()
    {
        String requestBody = "post test";

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("MyRequestHeader","MyValue");
        HttpEntity formEntity = new HttpEntity(headers);

        String result = restTemplate.postForObject("http://localhost:8080/Spring4RestfulDemo/sayhi?name={name}", formEntity, String.class, "GJF");

        System.out.println(result);

    }

    @Test
    public void postMethodTest2()
    {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("MyRequestHeader","MyValue");
        User user= new User("刻苦学习",32,32);
        HttpEntity<User> formEntity = new HttpEntity<User>(user,headers);
        Message result = restTemplate.postForObject("http://localhost:8080/Spring4RestfulDemo/user/add", formEntity, Message.class);
        System.out.println(result);

    }
    @Test
    public void postMethodTest3()
    {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("MyRequestHeader","MyValue");

//        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<String, Object>();
//        postParameters.add("id",333);
//        postParameters.add("name", "GJF-TEST");
//        postParameters.add("age",33);
//        postParameters.add("salary",30);
//
//        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(postParameters,headers);

        User user= new User("刻苦学习",32,32);

        String json=JSON.toJSONString(user);
        System.out.println(json);
        HttpEntity<String> formEntity = new HttpEntity<String>(json,headers);

        Message result = restTemplate.postForObject("http://localhost:8080/Spring4RestfulDemo/user/add", formEntity, Message.class);

        System.out.println(result);

    }

    @Value("#{thirdparty}")
    private Properties thirdpartyProperty;

    @Test
    public void fun()
    {
       // Message message=new Message<String>(true,null,"预约成功！");
        //System.out.println(JSON.toJSONString(message));

        String value=thirdpartyProperty.getProperty("key1");
        System.out.println(value);
    }
}
