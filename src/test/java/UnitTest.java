
import com.joybike.server.api.Message;
import com.joybike.server.api.dao.VehicleHeartbeatDao;
import com.joybike.server.api.dto.H5PostMessageDto;
import com.joybike.server.api.dto.Token;
import com.joybike.server.api.model.User;
import com.joybike.server.api.model.vehicleHeartbeat;
import com.joybike.server.api.util.UnixTimeUtils;

import myTest.Amortize;
import myTest.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
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


        com.joybike.server.api.model.Message<Token> message=null;//new com.joybike.server.api.model.Message<userInfo>(false,ReturnEnum.Iphone_Validate_Error.getErrorCode(), ReturnEnum.Iphone_Validate_Error.getErrorDesc(), null);
//
        //message =new com.joybike.server.api.model.Message<String>(true,0,null,null);
//        //new com.joybike.server.api.model.Message<String>(false, ReturnEnum.Iphone_Error.getErrorCode(),ReturnEnum.Iphone_Error.getErrorDesc(), null);
//                //new com.joybike.server.api.model.Message<userInfo>(true, 0,null, new userInfo());

        message =new com.joybike.server.api.model.Message<Token>(true,0,null,new Token("afdadsg23432etyuio",7200));
        System.out.println(JSON.toJSON(message));
//        System.out.println(message);

        //ThirdPayBean

    }




    @Test
    public void httpPost()
    {
//        String requestBody = "post test";
//
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("MyRequestHeader","MyValue");
        String url="http://amortize.web.58dns.org/consume?payId=178538428072626884&consumeTime=1472525116000&consumeAmount=4.00&totalAmount=54.00&isLastTime=1&flowId=947671856&productId=null&cateOne=2&cateTwo=null&cateThree=null&cityId=-1&userId=1050848302&sourceSys='gj'";
        String contents = url.substring(38);
        String[] kv=contents.split("&");

        Amortize amortize = new Amortize();
        int i=0;
        for (String item : kv)
        {
            String k=item.split("=")[0];
            String v=item.split("=")[1];

            System.out.println(k+"="+v);

            ++i;
            switch (i)
            {
                case 1:
                    amortize.setPayId(Long.valueOf(v));
                    break;

                case 2:
                    amortize.setConsumeTime(Long.valueOf(v));
                    break;
                case 3:
                    amortize.setConsumeAmount(BigDecimal.valueOf(Double.valueOf(v)));
                    break;
                case 4:
                    amortize.setTotalAmount(BigDecimal.valueOf(Double.valueOf(v)));
                    break;
                case 5:
                    amortize.setIsLastTime(Integer.valueOf(v));
                    break;
                case 6:
                    amortize.setFlowId(Integer.valueOf(v));
                    break;
                case 7:
                    //amortize.setProductId(Integer.valueOf(v));
                    break;
                case 8:
                    amortize.setCateOne(Integer.valueOf(v));
                    break;
                case 9:
                case 10:
                    break;
                case 11:
                    amortize.setCityId(Integer.valueOf(v));
                    break;
                case 12:
                    amortize.setUserId(Long.valueOf(v));
                    break;
                case 13:
                    amortize.setSourceSys("gj");
                    break;
                default:
                    break;
            }
        }

        System.out.println(amortize);


        HttpEntity<Amortize> formEntity = new HttpEntity<Amortize>(amortize,headers);
        String result = restTemplate.postForObject("http://amortize.web.58dns.org/consume", formEntity, String.class);
        System.out.println(result+"++++++++++++++++++");
    }



    @Test
    public void postH5()
    {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("MyRequestHeader","MyValue");
        String url="http://h5.joybike.com.cn/forward/service/pushMessage";
        H5PostMessageDto dto = new H5PostMessageDto("0210XOfx1zozqd03qQex1mpQfx10XOfy","1","15110184829","2016","1.0",null,"39");

        System.out.println(JSON.toJSONString(dto));
//        HttpEntity<H5PostMessageDto> formEntity = new HttpEntity<H5PostMessageDto>(dto,headers);
//        String result = restTemplate.postForObject(url, formEntity, String.class);

        String result = HttpRequest.sendPost(url, JSON.toJSONString(dto));

        System.out.println(result+"++++++++++++++++++");
    }



    @Autowired
    private VehicleHeartbeatDao vehicleHeartbeatDao;
    @Test
    public void postTest()
    {
        String param = "a1e4a729e9cd4c9fafa35c536108703e;000000000,01,ff,1,700103113732,4530,8249,,,3,0,1,0,50,";
        String token = param.split(";")[0];

        String content = param.split(";")[1];
        String[] values = content.split(",");

        vehicleHeartbeat heartbeat = new vehicleHeartbeat();

        heartbeat.setLockId(Long.valueOf(values[0]));
        heartbeat.setFirmwareVersion(values[1]);
        heartbeat.setAllocation(values[2]);
        heartbeat.setBaseStationType(values[3]);
        if (values[3].equals("0")) {
            heartbeat.setGpsTime(Long.valueOf(values[4]));
            heartbeat.setDimension(BigDecimal.valueOf(Double.valueOf(values[5])));
            heartbeat.setLongitude(BigDecimal.valueOf(Double.valueOf(values[6])));
        }
        if (values[3].equals("1")) {
            heartbeat.setLockTime(Long.valueOf(values[4]));
            heartbeat.setCellId(values[5]);
            heartbeat.setStationId(values[6]);
        }

        heartbeat.setSpeed(values[7]);
        heartbeat.setDirection(values[8]);
        heartbeat.setArousalType(Integer.valueOf(values[9]));
        heartbeat.setCustom(values[10]);
        heartbeat.setLockStatus(Integer.valueOf(values[11]));
        heartbeat.setBatteryStatus(Integer.valueOf(values[12]));
        heartbeat.setBatteryPercent(values[13]);
        heartbeat.setCreateAt(UnixTimeUtils.now());
        vehicleHeartbeatDao.save(heartbeat);
    }



}