package com.joybike.server.api.ThirdPayService.wechat;

import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取微信用户的OpenId
 * Created by 58 on 2016/10/26.
 */
public class wechatHelper {

    /**
     * 获取微信用户的Openid
     * @param code
     * @return
     */
    public  static String getOpenId(String code)
    {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("appid", WeiXinConfig.WECHAT_WEB_APP_ID));
        list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET));
        list.add(new BasicNameValuePair("code",code));
        list.add(new BasicNameValuePair("grant_type","authorization_code"));
        String res = HttpUtils.post(WeiXinConfig.TOKEN_URL, list);
        JSONObject tokenJson=JSONObject.fromObject(res);
        String openid = tokenJson.get("openid").toString();
        return openid;
    }
}
