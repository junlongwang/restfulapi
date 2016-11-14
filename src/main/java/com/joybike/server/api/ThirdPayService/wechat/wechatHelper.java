package com.joybike.server.api.ThirdPayService.wechat;

import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取微信用户的OpenId
 * Created by 58 on 2016/10/26.
 */
public class wechatHelper {
    private static String access_token=null;
    private static long  access_token_expire_time=0;
    private static String jsapi_ticket = null;
    private static long jsapi_ticket_expire_time = 0;

    /**
     * 获取微信用户的Openid
     * @param code
     * @return
     */
    public static String getOpenId(String code)
    {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("appid", WeiXinConfig.WECHAT_WEB_APP_ID));
        list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET));
        list.add(new BasicNameValuePair("code",code));
        list.add(new BasicNameValuePair("grant_type","authorization_code"));
        String res = HttpUtils.post(WeiXinConfig.TOKEN_URL, list);
        JSONObject tokenJson=JSONObject.fromObject(res);
        if(tokenJson.containsKey("errcode")){
            return null;
        }else{
            String openid = tokenJson.get("openid").toString();
            return openid;
        }
    }
    public static String getAccessToken() {
        if (access_token_expire_time < System.currentTimeMillis()) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("grant_type", "client_credential"));
            list.add(new BasicNameValuePair("appid",WeiXinConfig.WECHAT_WEB_APP_ID));
            list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET));
			String res = HttpUtils.post("https://api.weixin.qq.com/cgi-bin/token", list);
            System.out.println("res>>>>>"+res);
            JSONObject json = JSONObject.fromObject(res);
            if(json != null && json.containsKey("access_token")) {
                access_token_expire_time=System.currentTimeMillis()+7000*1000;
                access_token=json.get("access_token").toString();
                System.out.println(access_token);
                System.out.println(access_token_expire_time);
            }
            return json.get("access_token").toString();
        }
        return access_token;
    }
    public static String getJsApiTicket() {
        // jsapi_ticket 应该全局存储与更新，以下代码以写入到文件中做示例
        if (jsapi_ticket_expire_time < System.currentTimeMillis()) {
            String accessToken = wechatHelper.getAccessToken();
            // 如果是企业号用以 URL 获取 ticket
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + accessToken;
            JSONObject json = new JSONObject().fromObject(HttpUtils.requestJsonData(url));
            if (json != null && json.get("ticket") != null && json.get("ticket").toString().trim().length() > 0) {
                jsapi_ticket = json.get("ticket").toString();
                jsapi_ticket_expire_time = System.currentTimeMillis() + 7000 * 1000;
            }
        }

        return jsapi_ticket;
    }

    public static Map<String, String> getSignPackage(String url) {
        String jsapi_ticket = getJsApiTicket();
        Map<String, String> map = new Sign().sign(jsapi_ticket, url);
        Map<String, String> map2 = new java.util.LinkedHashMap<String, String>();
        map2.put("appid", WeiXinConfig.WECHAT_WEB_APP_ID);
//      map2.put("access_token", wechatHelper.getAccessToken());
        map2.putAll(map);
        return map2;
    }
}
