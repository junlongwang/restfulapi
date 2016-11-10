
package com.joybike.server.api.restful;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joybike.server.api.ThirdPayService.wechat.HttpUtils;
import com.joybike.server.api.ThirdPayService.wechat.JsSdk;
import com.joybike.server.api.ThirdPayService.wechat.WeiXinConfig;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wx")
@RestController()
public class WxRestfulApi {

	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(WxRestfulApi.class);
	
//*********************************************************微信js-config信息*******************************************************************	 
	@RequestMapping(value = "/getWaChatConfig")
	public void getWaChatConfig(HttpServletRequest request,HttpServletResponse response,String url) throws Exception{
		Map<String, Object> resMap = new HashMap<String, Object>();
		try{
			JsSdk js = new JsSdk();
			resMap.put("status", "ok");
			resMap.put("params", js.getSignPackage(url));
		} catch (Exception e) {
			resMap.put("status", "fail");
			//ErrorCode.addError(resMap, ErrorCode.SYS40001, ErrorCode.SYS40001_INFO);
			logger.error("微信获取配置权限参数异常", e);
		}
	}	
//*********************************************************获取用户信息*******************************************************************	 
	@RequestMapping("/code")//获取code
	public String code(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
		 	String state = "STATE"; 
			String baseUrl = WeiXinConfig.AUTHOR_URL;
			String finUrl = "?appid="+WeiXinConfig.WECHAT_WEB_APP_ID+"&redirect_uri="+URLEncoder.encode("http://api.joybike.com.cn/restful/wx/info?url="+url, "utf-8")+"&response_type=code&scope=snsapi_userinfo&state="+state+"#wechat_redirect";
			return "redirect:" + baseUrl+finUrl;
	 }
	
	 	@RequestMapping("/info")//获取用户信息
		public String info(HttpServletRequest request, HttpServletResponse response,String code,String url) throws Exception {
		    System.out.println(url);
		 	List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("appid", WeiXinConfig.WECHAT_WEB_APP_ID));  
			list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET)); 
			list.add(new BasicNameValuePair("code",code)); 
			list.add(new BasicNameValuePair("grant_type","authorization_code")); 
			String res = HttpUtils.post(WeiXinConfig.TOKEN_URL, list);
			JSONObject tokenJson=JSONObject.fromObject(res);
			System.out.println("token is "+ tokenJson);
			if(tokenJson.containsKey("errcode")){
				return null;
			}
			String openid = tokenJson.get("openid").toString();
			String access_token = tokenJson.get("access_token").toString();
			
			 String info_url = WeiXinConfig.USERINFO_URL+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
			 JSONObject userInfoJO = HttpUtils.requestJsonData(info_url);
		        
	        String wechatOpenId=userInfoJO.getString("openid");
	        String user_nickname=userInfoJO.getString("nickname");
	        String user_headimgurl=userInfoJO.getString("headimgurl");
	        return "redirect:"+url+"?openid="+openid+"&headimgurl="+user_headimgurl;
		 }
	 
	//*********************************************************获取用户信息*******************************************************************	 
		@RequestMapping("/base/code")//获取code
		public String baseCode(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
			 	String state = "STATE";
				String baseUrl = WeiXinConfig.AUTHOR_URL;
				String finUrl = "?appid="+WeiXinConfig.WECHAT_WEB_APP_ID+"&redirect_uri="+URLEncoder.encode("http://api.joybike.com.cn/restful/wx/base/info?url="+url, "utf-8")+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
				return "redirect:" + baseUrl+finUrl;
		 }
		
		 @RequestMapping("/base/info")//获取用户信息
			public String baseInfo(HttpServletRequest request, HttpServletResponse response,String code,String url) throws Exception {
			 	List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("appid", WeiXinConfig.WECHAT_WEB_APP_ID));  
				list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET)); 
				list.add(new BasicNameValuePair("code",code)); 
				list.add(new BasicNameValuePair("grant_type","authorization_code")); 
				String res = HttpUtils.post(WeiXinConfig.TOKEN_URL, list);
				JSONObject tokenJson=JSONObject.fromObject(res);
				String openid = tokenJson.get("openid").toString();
				String access_token = getAccessToken().toString();
				
				 String info_url = WeiXinConfig.USERINFO_URL_BASE+"?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
				 JSONObject userInfoJO= HttpUtils.requestJsonData(info_url);
			        
		        String user_openid=userInfoJO.getString("openid");
		        String user_nickname=userInfoJO.getString("nickname");
		        String user_headimgurl=userInfoJO.getString("headimgurl");
		        System.out.println(user_openid);
		        System.out.println(user_nickname);
		        System.out.println(user_headimgurl);
		        System.out.println(url+"?openid="+openid);
		        return "redirect:"+url+"?openid="+openid+"&headimgurl="+user_headimgurl;
			 }
		 
		 public static String getAccessToken() {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("grant_type", "client_credential"));  
				list.add(new BasicNameValuePair("appid",WeiXinConfig.WECHAT_WEB_APP_ID)); 
				list.add(new BasicNameValuePair("secret",WeiXinConfig.WECHAT_WEB_APP_SECRET)); 
				String res = HttpUtils.post("https://api.weixin.qq.com/cgi-bin/token", list);
				JSONObject json = JSONObject.fromObject(res);
				if (json != null && json.containsKey("access_token")) {
					return json.getString("access_token");
				}
			return null;
		}
}
