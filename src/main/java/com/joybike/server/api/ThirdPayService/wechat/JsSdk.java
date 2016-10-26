package com.joybike.server.api.ThirdPayService.wechat;

import java.util.Map;

import net.sf.json.JSONObject;

public class JsSdk {
	
	  private static  String  jsapi_ticket=null;
	  private static long  jsapi_ticket_expire_time=0;
	  private static String  access_token=null;
	  private static long  access_token_expire_time=0;
	  
	  private String  getAccessToken(){
		    // access_token 应该全局存储与更新，以下代码以写入到文件中做示例
		    if (access_token_expire_time < System.currentTimeMillis()) {
		      // 如果是企业号用以下URL获取access_token
		      String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WeiXinConfig.WECHAT_WEB_APP_ID+"&secret="+WeiXinConfig.WECHAT_WEB_APP_SECRET;
		      JSONObject json = HttpUtils.requestJsonData(url);
		      if (json!=null&&json.get("access_token")!=null&&json.get("access_token").toString().trim().length()>0) {
		    	  access_token=json.get("access_token").toString();
		    	  access_token_expire_time=System.currentTimeMillis()+7000*1000;
		      }
		    } 
		    return access_token;
	  }
	  public String getJsApiTicket() {
		    // jsapi_ticket 应该全局存储与更新，以下代码以写入到文件中做示例
		    if (jsapi_ticket_expire_time<System.currentTimeMillis()) {
		      String accessToken = getAccessToken();
		      // 如果是企业号用以�? URL 获取 ticket
		      String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+accessToken;
		      JSONObject json= HttpUtils.requestJsonData(url);
		      if (json!=null&&json.get("ticket")!=null&&json.get("ticket").toString().trim().length()>0) {
		    	  jsapi_ticket=json.get("ticket").toString();
		    	  jsapi_ticket_expire_time=System.currentTimeMillis()+7000*1000;
			      }
		    }  

		    return jsapi_ticket;
		  }
	  	public  Map<String,String> getSignPackage(String url) {
	  		String jsapi_ticket=getJsApiTicket();
	  		Map<String,String> map= new Sign().sign(jsapi_ticket, url);
	  		Map<String,String> map2=new java.util.LinkedHashMap<String, String>();
	  		map2.put("appid", WeiXinConfig.WECHAT_WEB_APP_ID);
	  		map2.put("accesstoken", access_token);
	  		map2.putAll(map);
	  		return map2;
	  	  }

}
