package com.joybike.server.api.thirdparty.wxtenpay.util;

public class ConstantUtil {
	/**
	 * 商家可以考虑读取配置文件
	 */
	
	//初始�?
	//public static String APP_ID = "wxd930ea5d5a258f4f";//微信�?发平台应用id
	public static String APP_ID = "wx60b0130b4498317b";//微信小额刷卡测试appid
	//public static String APP_SECRET = "db426a9829e4b49a0dcac7b4162da6b6";//应用对应的凭�?
	//public static String APP_SECRET = "db426a9829e4b49a0dcac7b4162da6b6";
	public static String APP_SECRET = "718f58d0cb02f95245239ab94ff12049";
	//应用对应的密�?
	//public static String APP_KEY = "L8LrMqqeGRxST5reouB0K66CaYAWpqhAVsq7ggKkxHCOastWksvuX1uvmvQclxaHoYd3ElNBrNO2DHnnzgfVG9Qs473M3DTOZug5er46FhuGofumV8H2FVR9qkjSlC5K";
	public static String APP_KEY = "avAqFwmBHs9wlKs5vFB4eCP10IpW0GDI80AHzlHcfJCwqtZPAxkfAy09HuvM6yVB9Ue8cQInvfiP0IBabkOFjUKWNE8hBrkENsHVejJbuspAzr0FcUgrdpMrYPa1ITAr";
	public static String PARTNER = "1900000109";//财付通商户号
	public static String PARTNER_KEY = "8934e7d15453e97507ef794cf7b0519d";//商户号对应的密钥
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url
	public static String GRANT_TYPE = "client_credential";//常量固定�? 
	public static String EXPIRE_ERRCODE = "42001";//access_token失效后请求返回的errcode
	public static String FAIL_ERRCODE = "40001";//重复获取导致上一次获取的access_token失效,返回错误�?
	public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";//获取预支付id的接口url
	public static String ACCESS_TOKEN = "access_token";//access_token常量�?
	public static String ERRORCODE = "errcode";//用来判断access_token是否失效的�??
	public static String SIGN_METHOD = "sha1";//签名算法常量�?
	//package常量�?
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
	public static String traceid = "testtraceid001";//测试用户id
}
