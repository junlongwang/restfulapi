package com.joybike.server.api.thirdparty.wxtenpay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

	public static String getJsonValue(String rescontent, String key) {
		JSONObject jsonObject;
		String v = null;
		try {
			jsonObject = JSON.parseObject(rescontent);
			v = jsonObject.getString(key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return v;
	}


	/**
	 * 将map的键值对转化成json串,value值不自动添加双引号（暂时用于支付宝app支付）
	 *
	 * @param paraMap
	 * @return
	 */
	public static String mapToJsonNoSinganaure(Map paraMap) {
		String jsonString = "{";
		if (paraMap != null && paraMap.size() > 0) {
			Set sets = paraMap.keySet();
			Iterator its = sets.iterator();
			while (its.hasNext()) {
				String key = its.next().toString();
				Object value = paraMap.get(key);
				if (value instanceof Map) {
					value = mapToJson((Map) value);
				}
				jsonString += "\"" + key + "\":" + value + ",";
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
		}
		jsonString += "}";
		return jsonString;
	}

	public static String mapToJson(Map paraMap) {
		String jsonString = "{";
		if (paraMap != null && paraMap.size() > 0) {
			Set sets = paraMap.keySet();
			Iterator its = sets.iterator();
			while (its.hasNext()) {
				String key = its.next().toString();
				Object value = paraMap.get(key);
				if (value instanceof Map) {
					value = mapToJson((Map) value);
				} else {
					value = "\"" + value + "\"";
				}
				jsonString += "\"" + key + "\":" + value + ",";
			}
			jsonString = jsonString.substring(0, jsonString.length() - 1);
		}
		jsonString += "}";
		return jsonString;
	}
}
