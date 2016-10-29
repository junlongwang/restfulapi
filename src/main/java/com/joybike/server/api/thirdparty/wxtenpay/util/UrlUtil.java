/* ========================================================
 * 北京五八信息�?术有限公司营运技术部�?发二�?
 * �? 期：2011-2-28
 * �? 者：李庆�?
 * �? 本：0.1
 * =========================================================
 */
package com.joybike.server.api.thirdparty.wxtenpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author liqingguo
 * 
 * @version 0.1
 */
public class UrlUtil {

	/**
	 * 连接超时
	 */
	private static int connectTimeOut = 5000;

	/**
	 * 读取数据超时
	 */
	private static int readTimeOut = 10000;

	public static String getUrl(Map map) {
		StringBuffer sb = new StringBuffer();
		for (Object entry : map.keySet()) {
			sb.append("<input type=\"hidden\" name=\"" + entry + "\" value=\""
					+ map.get(entry) + "\" />");
		}
		return sb.toString();
	}

	public static InputStream doPostForIS(String reqUrl, Map parameters,
			String recvEncoding) {
		return UrlUtil.doPost(reqUrl, parameters, recvEncoding);
	}

	public static String doPostForStr(String reqUrl, Map parameters,
			String recvEncoding) throws IOException {
		InputStream in = UrlUtil.doPost(reqUrl, parameters, recvEncoding);
		BufferedReader rd = new BufferedReader(new InputStreamReader(in,
				recvEncoding));
		String tempLine = rd.readLine();
		StringBuffer tempStr = new StringBuffer();
		String crlf = System.getProperty("line.separator");
		while (tempLine != null) {
			tempStr.append(tempLine);
			tempStr.append(crlf);
			tempLine = rd.readLine();
		}
		String temp = tempStr.toString();
		rd.close();
		in.close();
		return temp;
	}

	/**
	 * 发�?�带参数的POST的HTTP请求
	 * 
	 * @param reqUrl
	 *            HTTP请求URL
	 * @param parameters
	 *            参数映射�?
	 * @return HTTP响应的字符串
	 */
	private static InputStream doPost(String reqUrl, Map parameters,
			String recvEncoding) {
		HttpURLConnection url_con = null;
		try {
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				for (Iterator iter = parameters.entrySet().iterator(); iter
						.hasNext();) {
					Entry element = (Entry) iter.next();
					params.append(element.getKey().toString());
					params.append("=");
					params.append(URLEncoder.encode(element.getValue()
							.toString(), recvEncoding));
					params.append("&");
				}
				if (params.length() > 0) {
					params = params.deleteCharAt(params.length() - 1);
				}
			}
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(20000);
			url_con.setReadTimeout(20000);
			url_con.setDoOutput(true);
			// TODO
			System.out.println("params==doPost>>>>>" + params);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			return in;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// if (url_con != null) {
			// url_con.disconnect();
			// }
		}
		return null;
	}

	public static String check(String urlvalue) {

		String inputLine = "";

		try {
			URL url = new URL(urlvalue);

			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);
			urlConnection.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));

			inputLine = in.readLine();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out
					.println("审批处理回调地址:" + urlvalue + "发生异常" + e.getMessage());
		}
		System.out.println("审批处理回调地址:" + urlvalue);
		return inputLine;
	}
    
	public static void main(String[] args) throws UnsupportedEncodingException {
		String aa = "\"";
		System.out.println(URLDecoder.decode(aa, "GBK"));
	}
}
