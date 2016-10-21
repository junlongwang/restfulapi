package com.joybike.server.api.thirdparty.wxtenpay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;

/**
 * 请求处理�?
 * 请求处理类继承此类，重写createSign方法即可�?
 * @author miklchen
 *
 */
public class RequestHandler {
	
	/** 网关url地址 */
	private String gateUrl;
	
	/** 密钥 */
	private String key;
	
	/** 请求的参�? */
	private SortedMap parameters;
	
	/** debug信息 */
	private String debugInfo;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	/**
	 * 构�?�函�?
	 * @param request
	 * @param response
	 */
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		this.gateUrl = "https://gw.tenpay.com/gateway/pay.htm";
		this.key = "";
		this.parameters = new TreeMap();
		this.debugInfo = "";
	}
	
	/**
	*初始化函数�??
	*/
	public void init() {
		//nothing to do
	}

	/**
	*获取入口地址,不包含参数�??
	*/
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	*设置入口地址,不包含参数�??
	*/
	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	/**
	*获取密钥
	*/
	public String getKey() {
		return key;
	}

	/**
	*设置密钥
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 获取参数�?
	 * @param parameter 参数名称
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * 设置参数�?
	 * @param parameter 参数名称
	 * @param parameterValue 参数�?
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * 返回�?有的参数
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {		
		return this.parameters;
	}

	/**
	*获取debug信息
	*/
	public String getDebugInfo() {
		return debugInfo;
	}
	
	/**
	 * 获取带参数的请求URL
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestURL() throws UnsupportedEncodingException {
		
		this.createSign();
		
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
			} else {
				sb.append(k + "=" + v.replace(".", "%2E") + "&");
				
				
			}
		}
		
		//去掉�?后一�?&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		
		return this.getGateUrl() + "?" + reqPars;
		
	}
	/**
	 * @time:2014-07-22
	 * @description:不带签名信息的组成串
	 * @param urlEncodeFlag
	 * @return
	 * @throws UnsupportedEncodingException
	 */
public String getRequestURLNoSign(boolean urlEncodeFlag) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" );
				if( urlEncodeFlag ) {
					sb.append(URLEncoder.encode(v, enc));
				}else{
					sb.append(v);
				}
				sb.append(  "&");
				
			} else {
				if( urlEncodeFlag )	sb.append(k + "=" + v.replace(".", "%2E") + "&");
				else sb.append(k).append("=").append(v).append("&");
				
				
			}
		}
		
		//去掉�?后一�?&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		
		return this.getGateUrl() + "?" + reqPars;
		
	}
	
	public void doSend() throws UnsupportedEncodingException, IOException {
		this.response.sendRedirect(this.getRequestURL());
	}
	
	/**
	 * 创建md5摘要,规则�?:按参数名称a-z排序,遇到空�?�的参数不参加签名�??
	 */
	protected void createSign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
	System.out.println("md5sign>>>>编码:"+enc+"\t 值："+sb.toString());
		this.setParameter("sign", sign);
		
		//debug信息
		this.setDebugInfo("md5str:" + sb.toString() + " => sign:" + sign);
		
	}
	
	/**
	*设置debug信息
	*/
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	
	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}
	
	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}
	 
}
