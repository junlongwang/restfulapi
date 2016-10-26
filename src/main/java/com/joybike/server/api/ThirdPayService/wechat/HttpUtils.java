package com.joybike.server.api.ThirdPayService.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;




public class HttpUtils {
	private final static Logger logger = Logger.getLogger(HttpUtils.class);
	
	public static String post(String url, List<NameValuePair> params) {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120*1000).setConnectTimeout(120*1000).build();
		post.setConfig(requestConfig);
		HttpResponse response = null; 
		String data = ""; 
		// 编码格式转换
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(entity);
			response = client.execute(post);
			logger.info("请求返回请求状态码：" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = response.getEntity();
				data = EntityUtils.toString(resEntity);
			}
		} catch (Exception e) {
			throw new RuntimeException("请求接口ERROR");
		}
		return data;
	}

	public static JSONObject requestJsonData(String url){
		
	 	String jsonInfo="";
	 	try {
	 		URL urls = new URL(url);
	 		// 得到HttpURLConnection对象
	 		HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
	 		// 设置为GET方式
	 		connection.setRequestMethod("GET");
	 		connection.setConnectTimeout(20000);
	 		connection.setReadTimeout(60000);
	 		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	 			// 得到响应消息
	            String message = connection.getContentType();
	            System.out.println("message="+message);
	            BufferedReader  isR= new BufferedReader( new InputStreamReader(connection.getInputStream()));
	     		while(isR.ready()){
	     			String abc=isR.readLine();
	     			jsonInfo+=abc;
	     		}
	            JSONObject obj=new JSONObject().fromObject(jsonInfo);
	            return obj;
	     	}else{
	     		//
	     	}
	 		if(connection!=null){
	 			connection.disconnect();
	 		}
	 	} catch (Exception err) {
	 		Logger.getRootLogger().info("jssdk异常|"+err.getMessage());
	 	}
	 	return null;
	}
    
//    public static String requestJsonData(String url) {
//		String jsonInfo = "";
//		try {
//			URL urls = new URL(url);
//			// 得到HttpURLConnection对象
//			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
//			// 设置为GET方式
//			connection.setRequestMethod("GET");
//			connection.setConnectTimeout(20000);
//			connection.setReadTimeout(60000);
//			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//				// 得到响应消息
//				String message = connection.getContentType();
//				System.out.println("message="+message);
//				BufferedReader isR = new BufferedReader(new InputStreamReader(
//						connection.getInputStream()));
//				while (isR.ready()) {
//					String abc = isR.readLine();
//					jsonInfo += abc;
//				}
//			} else {
//				//
//			}
//		} catch (Exception err) {
//			System.out.println("获取请求数据异常" + err.getMessage());
//		}
//		return jsonInfo;
//	}
}