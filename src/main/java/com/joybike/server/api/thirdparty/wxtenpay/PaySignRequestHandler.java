package com.joybike.server.api.thirdparty.wxtenpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;

public class PaySignRequestHandler extends RequestHandler {

	public PaySignRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
		// TODO Auto-generated constructor stub
	}
	
	public String getPaySign() throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(super.getHttpServletRequest(), super.getHttpServletResponse());
		System.out.println("enc>>>>"+enc);
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"spbill_create_ip".equals(k)) {
				//sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
				sb.append(k + "=" +v + "&");
			} else {
				sb.append(k + "=" + v.replace(".", "%2E") + "&");
			}
		}
		//去掉�?后一�?&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		String sha1sign =Sha1Util.getSha1(reqPars); 
		super.setDebugInfo("sha1strs:" + reqPars + " => sha1sign:" + sha1sign);
		return sha1sign;
	}
	/**
	 * 创建签名SHA1
	 *
	 * @return
	 * @throws Exception
	 */
	public String createSHA1Sign() {
		StringBuffer sb = new StringBuffer();
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
	
		String params = sb.substring(0, sb.lastIndexOf("&"));
		System.out.println("参与签名原始串：" +params);
		String appsign = Sha1Util.getSha1(params);
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
		return appsign;
	}
	
}
