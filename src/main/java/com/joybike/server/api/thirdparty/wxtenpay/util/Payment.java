package com.joybike.server.api.thirdparty.wxtenpay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment {
	public static String CreateUrl1(String paygateway, String input_charset,
			String service, String partner, String sign_type, String email,
			String key) {

		Map params = new HashMap();
		params.put("_input_charset", input_charset);
		params.put("service", service);
		params.put("partner", partner);
		params.put("email", email);

		String prestr = "";

		prestr = prestr + key;
		// System.out.println("prestr=" + prestr);

		String sign = MD5.encode(getContent(params, key));

		String parameter = "";
		parameter = parameter + paygateway;

		List keys = new ArrayList(params.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String value = (String) params.get(keys.get(i));
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			try {
				parameter = parameter + keys.get(i) + "="
						+ URLEncoder.encode(value, input_charset) + "&";
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}

		parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

		return parameter;

	}

	public static String CreateUrl(String paygateway, String input_charset,
			String service, String partner, String sign_type, String batch_no,
			String refund_date, String batch_num, String detail_data,
			String notify_url, String key, String dbacknotifyUrl) {

		Map params = new HashMap();
		params.put("_input_charset", input_charset);
		params.put("service", service);
		params.put("partner", partner);
		params.put("batch_no", batch_no);
		params.put("refund_date", refund_date);
		params.put("batch_num", batch_num);
		params.put("detail_data", detail_data);
		params.put("notify_url", notify_url);
		params.put("dback_notify_url", dbacknotifyUrl);
		String prestr = "";

		prestr = prestr + key;
		// System.out.println("prestr=" + prestr);

		String sign = MD5.encode(getContent(params, key));

		String parameter = "";
		parameter = parameter + paygateway;

		List keys = new ArrayList(params.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String value = (String) params.get(keys.get(i));
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			try {
				parameter = parameter + keys.get(i) + "="
						+ URLEncoder.encode(value, input_charset) + "&";
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			}
		}

		parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

		return parameter;

	}

	private static String getContent(Map params, String privateKey) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
		return prestr + privateKey;
	}
}