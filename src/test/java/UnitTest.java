import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Message;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.User;
import com.joybike.server.api.model.userInfo;
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

//        String value=thirdpartyProperty.getProperty("key1");
//        System.out.println(value);
//
//
//
//        //vehicleRepair(String vehicleId, String cause, String faultImg, Long createId, Integer createAt, Integer disposeStatus, String disposeDepict, Long operateId, Integer operateAt) {
//        vehicleRepairDto vehicleRepair=new vehicleRepairDto("车辆编码","车辆没轮子",value.getBytes(),333L, UnixTimeUtils.now());
//        System.out.println(JSON.toJSON(vehicleRepair));
//
//        System.out.println(JSON.toJSON(new com.joybike.server.api.model.Message<String>(true, 0, null, "提交成功！")));
//
//        System.out.println(JSON.toJSON(new com.joybike.server.api.model.Message<String>(false, ReturnEnum.Submit_Error.getErrorCode(), ReturnEnum.Submit_Error.getErrorDesc(), null)));
//
//        System.out.println(ReturnEnum.Submit_Error.getErrorCode());
//        System.out.println(ReturnEnum.Submit_Error.getErrorDesc());
//
//
//        userInfoDto dto = new userInfoDto(111L,"手机号码","真实姓名","身份证号","国籍","人和身份证".getBytes(),"身份证图片".getBytes(),"头像".getBytes());
//        System.out.println(JSON.toJSON(dto));


        //com.joybike.server.api.model.Message<userInfo> message=new com.joybike.server.api.model.Message<userInfo>(false, ReturnEnum.UpdateUer_ERROR.getErrorCode(),ReturnEnum.UpdateUer_ERROR.getErrorDesc(),null);


//        com.joybike.server.api.model.Message<String> message=null;//new com.joybike.server.api.model.Message<userInfo>(false,ReturnEnum.Iphone_Validate_Error.getErrorCode(), ReturnEnum.Iphone_Validate_Error.getErrorDesc(), null);
//
//        message =new com.joybike.server.api.model.Message<String>(true,0,null,null);
//        //new com.joybike.server.api.model.Message<String>(false, ReturnEnum.Iphone_Error.getErrorCode(),ReturnEnum.Iphone_Error.getErrorDesc(), null);
//                //new com.joybike.server.api.model.Message<userInfo>(true, 0,null, new userInfo());
//        System.out.println(JSON.toJSON(message));
//        System.out.println(message);

        //ThirdPayBean

    }
}
