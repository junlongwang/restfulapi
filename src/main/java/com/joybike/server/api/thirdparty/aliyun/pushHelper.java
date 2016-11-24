package com.joybike.server.api.thirdparty.aliyun;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidResponse;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToiOSResponse;
import com.joybike.server.api.dto.H5PostMessageDto;
import com.joybike.server.api.restful.BicycleRestfulApi;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

/**
 * Created by 58 on 2016/11/20.
 */
public class pushHelper {

    private static Logger logger = Logger.getLogger(pushHelper.class);

    private static final String REGION_HANGZHOU = "cn-hangzhou";

    protected static long appKey;
    protected static String deviceIds;
    protected static String accounts;
    protected static String tagExpression;

    protected static DefaultAcsClient client;

    /**
     * 从配置文件中读取配置值，初始化Client
     * <p>
     * 1. 如何获取 accessKeyId/accessKeySecret/appKey 照见README.md 中的说明<br/>
     * 2. 先在 push.properties 配置文件中 填入你的获取的值
     */
    static  {
        InputStream inputStream = pushHelper.class.getClassLoader().getResourceAsStream("push.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String accessKeyId = properties.getProperty("accessKeyId");
        assertNotNull("先在 push.properties 配置文件中配置 accessKeyId", accessKeyId);

        String accessKeySecret = properties.getProperty("accessKeySecret");
        assertNotNull("先在 push.properties 配置文件中配置 accessKeySecret", accessKeySecret);

        String key = properties.getProperty("appKey");
        assertNotNull("先在 push.properties 配置文件中配置 appKey", key);
        appKey = Long.valueOf(key);

        deviceIds = properties.getProperty("deviceIds");
        accounts = properties.getProperty("accounts");
        tagExpression = properties.getProperty("tagExpression");

        IClientProfile profile = DefaultProfile.getProfile(REGION_HANGZHOU, accessKeyId, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }

    public static void PushMessageToIOS(String content,String target) throws Exception {
        PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
        iOSRequest.setAppKey(appKey);
        iOSRequest.setTarget("device");
        if(target==null) {
            iOSRequest.setTargetValue("all");
        }
        else
        {
            iOSRequest.setTargetValue(target);
        }
        iOSRequest.setMessage(content);
        iOSRequest.setSummary(content);

        PushMessageToiOSResponse pushMessageToiOSResponse = client.getAcsResponse(iOSRequest);
        System.out.println("RequestId: " + pushMessageToiOSResponse.getRequestId() + "ResponseId:" + pushMessageToiOSResponse.getResponseId());
        logger.info("RequestId: " + pushMessageToiOSResponse.getRequestId() + "ResponseId:" + pushMessageToiOSResponse.getResponseId());
    }

    /**
     * 推送消息给android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/message-to-android.html
     */
    public static void PushMessageToAndroid(String content,String target) throws Exception {
        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        androidRequest.setAppKey(appKey);
        androidRequest.setTarget("device");
        androidRequest.setTargetValue(target);
        androidRequest.setMessage(content);
        PushMessageToAndroidResponse pushMessageToAndroidResponse = client.getAcsResponse(androidRequest);
        logger.info("RequestId: " + pushMessageToAndroidResponse.getRequestId() + "ResponseId:" + pushMessageToAndroidResponse.getResponseId());
    }

    public static void PushMessageToH5(H5PostMessageDto dto) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("MyRequestHeader", "MyValue");
        String url = "http://h5.joybike.com.cn/forward/service/pushMessage";
        HttpEntity<H5PostMessageDto> formEntity = new HttpEntity<H5PostMessageDto>(dto,headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        logger.info("$$$$$$$$$$$$$$H5消息推送$$$$$$$$$$$$$$");
        logger.info(JSON.toJSONString(dto));
    }
}
