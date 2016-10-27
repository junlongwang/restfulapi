package com.joybike.server.api.ThirdPayService.wechat;

public class WeiXinConfig {
	 public static String TRADE_TYPE	= "JSAPI" ;//交易类型
	 public static String WECHAT_WEB_PARTNER	= "1401808502" ;//商户
	 public static String WECHAT_WEB_APP_ID		= "wxa8d72207b41a315e";	//公众号APPID
	 public static String WECHAT_WEB_APP_SECRET	= "853D02D2F946329243B006C933A12E65";  //公众号APPSECRET
	 
	 public static String AUTHOR_URL	= "https://open.weixin.qq.com/connect/oauth2/authorize";//网页授权获取code
	 public static String TOKEN_URL		= "https://api.weixin.qq.com/sns/oauth2/access_token";//根据上面的code获取openid
	 public static String USERINFO_URL	= "https://api.weixin.qq.com/sns/userinfo";//获取用户信息
	 public static String USERINFO_URL_BASE	= "https://api.weixin.qq.com/cgi-bin/user/info";//获取用户信息
	 
	 
}
