package com.joybike.server.api.thirdparty.wxtenpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.joybike.server.api.thirdparty.wxtenpay.util.*;

public class PackageRequestHandler extends RequestHandler{
	
	public PackageRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	public String getPackageValue() throws UnsupportedEncodingException{
		//创建签名
		super.createSign();
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(super.getHttpServletRequest(), super.getHttpServletResponse());
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			System.out.println("packageValue,encode之前:"+k+"="+v);
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" + URLEncoder.encode(v, enc) + "&"); 
			} else {
				sb.append(k + "=" + v.replace(".", "%2E") + "&");
			}
		}
		
		//去掉�?后一�?&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
	System.out.println("package:编码�?"+enc+"\t reqPars>>>>"+reqPars); 
		return reqPars;
	}
}
