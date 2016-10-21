package com.joybike.server.api.thirdparty;

import com.alibaba.fastjson.JSON;
import com.huaxincloud.utils.HuaXinUtils;
import com.huaxincloud.utils.RequestParametersHolder;
import com.huaxincloud.utils.map.HuaXinMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 短信发送功能类
 * Created by 58 on 2016/10/20.
 */
public class SMSSender {

    static private String appSecret="77fc5dca847c472f876ecd93d7d147b1";;


    /**
     * 给手机发送短信
     * @param mobile
     * @param content
     */
    static SMSResponse sendMessage(String mobile,String content) {
        HuaXinMap hxmap = new HuaXinMap();
        String str=null;
        try {
            str= "[{\"data\":\""+content+"\",\"destination\":\""+mobile+"\"}]";
            str= URLEncoder.encode(str, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        hxmap.put("dynadatas",str);
        hxmap.put("productId", "B2016000493");
        hxmap.put("templateId", "SMS20160923729");//
        hxmap.put("signingId", "1474596563933063");
        //hxmap.put("callbackUrl", "http://YOURWebSite.com:8080/callback_url");
        //需要回调，就加上你的回调地址,不需要就不要加
        return process((Map<String, String>) hxmap, appSecret);
    }
    static SMSResponse process(Map<String,String> mapParams,String secret)
    {

        try {
            //权限校验
            HuaXinMap hxmap = new HuaXinMap();
            RequestParametersHolder requestHolder = new RequestParametersHolder();
            requestHolder.setApplicationParams(hxmap);
            hxmap.put("timestamp", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
            hxmap.put("sign_method","hmac");
            hxmap.putAll(mapParams);
            try {
                boolean isHmac = "hmac".equalsIgnoreCase(hxmap.get("sign_method"));
                String signBringUp = HuaXinUtils.signTopRequestNew(requestHolder, secret, isHmac);
                hxmap.put("sign",signBringUp);
            } catch (IOException e) {
                e.printStackTrace();
            }


            String sb= "http://api.huaxincloud.com:8081/custom/CI2016000403/sms/putDynaSms/";
            //String sb= "http://api.huaxincloud.com:8081/custom/"+custId+"/sms/"+actionName+"/";
            URL url = new URL(sb);


            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url.toString());

            // 设置header
            StringEntity se = new StringEntity(JSON.toJSONString(hxmap));
            se.setContentType("application/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8"));


            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(se);
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            // 返回处理
            try {
                HttpEntity entity2 = response2.getEntity();
                String ret = EntityUtils.toString(entity2);
                System.out.println("ret:" + ret);
                EntityUtils.consume(entity2);

                return JSON.parseObject(ret,SMSResponse.class);
            } catch (IOException e) {
                System.out.println("执行HTTP Post请求" + url.toString() + "时，发生异常！");
            } finally {
                response2.close();
            }

        } catch (IOException e) {
            System.out.println("内部错误，发生异常！"+e.getMessage());
        }
        return null;
    }
    public static void main(String [] args) {

        try {
            sendMessage("15110184829","123");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
