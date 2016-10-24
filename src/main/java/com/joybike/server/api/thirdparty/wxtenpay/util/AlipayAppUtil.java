package com.joybike.server.api.thirdparty.wxtenpay.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class AlipayAppUtil {

	/**
	 * 把数组所有元素排序，并按照�?�参�?=参数值�?�的模式用�??&”字符拼接成字符�?
	 * 
	 * @param params
	 *            �?要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一�?&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	/**
	 * 验证支付结果
	 * @param tradeStatus
	 * @return
	 */
	public static boolean checkTradeSucAndFin(String tradeStatus){
		 boolean flag=false;
		 
		 if(StringUtils.isNotEmpty(tradeStatus)&&!"null".equals(tradeStatus)){
			 flag=tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")?true:false;
		 }
		
		 return flag;
	}
	
	/** 
     * 除去数组中的空�?�和签名参数
     * @param sArray 签名参数�?
     * @return 去掉空�?�与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    
}
