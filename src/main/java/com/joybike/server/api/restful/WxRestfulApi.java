
package com.joybike.server.api.restful;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.ThirdPayService.wechat.HttpUtils;
import com.joybike.server.api.ThirdPayService.wechat.JsSdk;
import com.joybike.server.api.ThirdPayService.wechat.WeiXinConfig;
import com.joybike.server.api.ThirdPayService.wechat.wechatHelper;
import com.joybike.server.api.model.Message;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/wechat")
public class WxRestfulApi {


	private final Logger logger = Logger.getLogger(WxRestfulApi.class);
	
//*********************************************************微信js-config信息*******************************************************************	 
	@RequestMapping(value = "/getWaChatConfig", method = RequestMethod.GET)
	public ResponseEntity<Message<Map<String,String>>> getWaChatConfig(@RequestParam("url") String url) {
		try {
            Map<String,String> map = wechatHelper.getSignPackage(url);
			return ResponseEntity.ok(new Message<Map<String,String>>(true, 0, null,map));
		} catch (Exception e) {
			return ResponseEntity.ok(new Message<Map<String,String>>(false, ReturnEnum.Acount_Error.getErrorCode(), ReturnEnum.Acount_Error.getErrorDesc() + "-" + e.getMessage(), null));
		}
	}
    //snsapi_userinfo 弹出授权页面，可通过openid拿到昵称、性别、所在地
    @RequestMapping("/snsapiuser")//获取code
    public String snsApiUser(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        String state = "STATE";
        String baseUrl = WeiXinConfig.AUTHOR_URL;
        String finUrl = "?appid="+WeiXinConfig.WECHAT_WEB_APP_ID+"&redirect_uri="+URLEncoder.encode("http://api.joybike.com.cn/restful/wechat/snsapiuserinfo?url="+url, "utf-8")+"&response_type=code&scope=snsapi_userinfo&state="+state+"#wechat_redirect";
        return "redirect:" + baseUrl+finUrl;
    }

    @RequestMapping("/snsapiuserinfo")//获取用户信息
    public String snsApiUserInfo(HttpServletRequest request, HttpServletResponse response,String code,String url) throws Exception {
        logger.info("get wechat snsapiuserinfo url is : "+ url);
        logger.info("get wechat snsapiuserinfo code is : "+ code);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("appid", WeiXinConfig.WECHAT_WEB_APP_ID));
        list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET));
        list.add(new BasicNameValuePair("code",code));
        list.add(new BasicNameValuePair("grant_type","authorization_code"));
        String res = HttpUtils.post(WeiXinConfig.TOKEN_URL, list);
        logger.info("get wechat oauth2 access_token is : "+ res.toString());
        JSONObject tokenJson=JSONObject.fromObject(res);
        if(tokenJson.containsKey("errcode")){
            return null;
        }
        String openid = tokenJson.get("openid").toString();
        String access_token = tokenJson.get("access_token").toString();
        String info_url = WeiXinConfig.USERINFO_URL+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        JSONObject userInfoJO = HttpUtils.requestJsonData(info_url);
        String nickname = userInfoJO.getString("nickname");
        String headimgurl = userInfoJO.getString("headimgurl");

        return "redirect:"+url+"?openid="+openid+"&headimgurl="+headimgurl+"&nickname="+nickname;
    }

    //snsapi_base 不弹出授权页面，直接跳转，只能获取用户openid
    @RequestMapping("/snsapibase")//获取code
    public String snsApiBase(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        String state = "STATE";
        String baseUrl = WeiXinConfig.AUTHOR_URL;
        String finUrl = "?appid="+WeiXinConfig.WECHAT_WEB_APP_ID+"&redirect_uri="+URLEncoder.encode("http://api.joybike.com.cn/restful/wechat/snsapibaseinfo?url="+url, "utf-8")+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
        return "redirect:" + baseUrl+finUrl;
    }

    @RequestMapping("/snsapibaseinfo")//获取用户信息
    public String snsApiBaseInfo(HttpServletRequest request, HttpServletResponse response,String code,String url) throws Exception {
        logger.info("get wechat snsapiuserinfo url is : "+ url);
        logger.info("get wechat snsapiuserinfo code is : "+ code);
        return "redirect:"+url+"?openid="+ wechatHelper.getOpenId(code);
    }

    //*********************************************************获取用户信息*******************************************************************
    @RequestMapping("/cgibin")//获取code
    public String cgiBin(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        String state = "STATE";
        String baseUrl = WeiXinConfig.AUTHOR_URL;
        String finUrl = "?appid="+WeiXinConfig.WECHAT_WEB_APP_ID+"&redirect_uri="+URLEncoder.encode("http://api.joybike.com.cn/restful/wechat/cgibinuserinfo?url="+url, "utf-8")+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
        return "redirect:" + baseUrl+finUrl;
    }

    @RequestMapping("/cgibinuserinfo")//获取用户信息
    public String cgiBinUserInfo(HttpServletRequest request, HttpServletResponse response,String code,String url) throws Exception {
        String openid = wechatHelper.getOpenId(code);
        String access_token = wechatHelper.getAccessToken();
        logger.info("get wechat global access_token is : "+access_token);
        String info_url = WeiXinConfig.USERINFO_URL_BASE+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        JSONObject userInfoJO = HttpUtils.requestJsonData(info_url);
        String subscribe=userInfoJO.getString("subscribe");
        if(subscribe.equals("1")){
            String user_openid=userInfoJO.getString("openid");
            String user_nickname=userInfoJO.getString("nickname");
            String user_headimgurl=userInfoJO.getString("headimgurl");
            return "redirect:"+url+"?openid="+openid+"&headimgurl="+user_headimgurl+"&subscribe="+subscribe;
        }else{
            return "redirect:"+url+"?openid="+openid+"&subscribe="+subscribe;
        }
    }

}
