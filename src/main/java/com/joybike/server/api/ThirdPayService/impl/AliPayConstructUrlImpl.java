package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import com.joybike.server.api.thirdparty.wxtenpay.util.RSASignature;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LongZiyuan on 2016/10/24.
 */
@Service
public class AliPayConstructUrlImpl implements AliPayConstructUrlInter{

    private String partner = "2088521096580226";
    private String account = "";
    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANkzPpaA1tYpcRXEq3l4ykaCeK5AZYj07n8EfdKkQxr6uo2Lzw8g5jwgFdkO330VtoVvClRnrsjF/d6WRiao/6slBIgDrvqEbWEXtudyqLzkl2DZGnmtQpC5q7q56P5bBAMfeli0ZmYW3rjdveGf1TRDGMYYR4x87V998XNA4UgNAgMBAAECgYAEAOXOcGGFYQ4skIt4mblgw1bmH1m/xIQA41xOXai+/pAhu8n9RWX5Bb5hWdzUuWm72+gc1ixqlvuu9qYkYEkWHcjZS4TqOANCqtSCWp4hlRGVCRfHtm1wDL72Z2AF7BZIRwPnRhS9apGm1kCSCH3iSYHJCijZS3T1ooPzWJ1dAQJBAP5A1PfmGvS2WbsCHy44Ib5Gd4JP82SuKLz/IqFu9tD23x7ZLc1zvQVkKfyBr5pFw6+lJ0t2IlSiPW68sdckyXUCQQDasT7V6ZCHxbgVrOb4C7CC4/ZVxUSLTYYKckjECrVKbov+DFaPojy2IGfoJGZaWJlk0FNeOBmQFKZouXJ1dBk5AkBYUlEo5GhMxeOZ0Pzf42PlYzk0rW1RdiZ0sPRou9FFedy8LJl6m0/4RXlIXAySPNXjeC2USy9V0x4gD7B/minZAkEAwQZS1NIrvHr6iT8sOeFvcYguI/RTFLVfSxcmPMrKyyCZtalEOdDTz1j4/YArSzEKa14pR28yuOZRHvwYF61amQJAA2GH8JB4p4rGx/yw0dY3ZDGJMpjOed3MZDnJ1blrDG3akdJM8qU8H/niH5uLm5zYjAk0VyUmg/9jXJyHFfjpsw==";
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZMz6WgNbWKXEVxKt5eMpGgniuQGWI9O5/BH3SpEMa+rqNi88PIOY8IBXZDt99FbaFbwpUZ67Ixf3elkYmqP+rJQSIA676hG1hF7bncqi85Jdg2Rp5rUKQuau6uej+WwQDH3pYtGZmFt643b3hn9U0QxjGGEeMfO1fffFzQOFIDQIDAQAB";
    private String ALIPAY_PAY_SERVICE = "mobile.securitypay.pay";
    private String notifyUrl = "http://api.joybike.com.cn/pay/paynotify";

    public RedirectParam getUrl(HashMap<String,String> paraMap) {
        RedirectParam para = new RedirectParam();
        HashMap<String, String> requestParams = new HashMap<String, String>();
        //封装提交的参数
        String signature="\"";
        String outTradeNo="";
        String body="";
        String subject="";
        String totalFee="";
        try{
            if (paraMap == null||paraMap.size()==0) {
                return null;
            }else{
                outTradeNo = paraMap.get("out_trade_no");
                if(StringUtil.isNullOrEmpty(outTradeNo)){
                    return null;
                }
                body=paraMap.containsKey("body")?String.valueOf(paraMap.get("body")):"";
                subject=paraMap.containsKey("subject")?String.valueOf(paraMap.get("subject")):"Joybike账户充值";
                totalFee=String.valueOf(paraMap.get("total_fee"));
                //客户端号，用于快登
                if(paraMap.containsKey("app_id")&&(!StringUtil.isNullOrEmpty(String.valueOf(paraMap.get("app_id"))))){
                    requestParams.put("app_id",signature+String.valueOf(paraMap.get("app_id"))+signature);
                }
                //客户端来源
                if(paraMap.containsKey("appenv")&&(!StringUtil.isNullOrEmpty(String.valueOf(paraMap.get("appenv"))))){
                    requestParams.put("appenv",signature+String.valueOf(paraMap.get("appenv"))+signature);
                }
                //超时时间
                if (paraMap.containsKey("it_b_pay") && (!StringUtil.isNullOrEmpty(String.valueOf(paraMap.get("it_b_pay"))))) {
                    requestParams.put("it_b_pay",signature+String.valueOf(paraMap.get("it_b_pay"))+signature);
                }else {
                    requestParams.put("it_b_pay", signature+"15d"+signature);
                }
                //商品展示地址
                if (paraMap.containsKey("show_url") && (!StringUtil.isNullOrEmpty(String.valueOf(paraMap.get("show_url"))))) {
                    requestParams.put("show_url",signature+String.valueOf(paraMap.get("show_url"))+signature);
                }
                requestParams.put("service",signature+ALIPAY_PAY_SERVICE+signature);
                requestParams.put("partner",signature+ partner +signature);
                requestParams.put("_input_charset",signature+"utf-8"+signature);
                //AlipayPropertiesConfigEnum.ALIPAY_NOTIFY_URL.getEnumValues()
                requestParams.put("notify_url",notifyUrl + "?"
                        + "attach" + "=" + paraMap.get("consumeid") + signature);
                requestParams.put("out_trade_no",signature+outTradeNo+signature);
                requestParams.put("subject",signature+subject+signature);
                requestParams.put("body",signature+body+signature);
                requestParams.put("payment_type",signature+ "1" +signature);
                requestParams.put("seller_id",signature+account+signature);//AlipayPropertiesConfigEnum.ALIPAY_SELLER_ID.getEnumValues()
                requestParams.put("total_fee",signature+totalFee+signature);
                String preSignStr = AlipayAppUtil.createLinkString(requestParams);
                String sign = RSASignature.sign(preSignStr, privateKey, "utf-8");
                try {
                    sign= URLEncoder.encode(sign, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                requestParams.put("sign", signature+sign+signature);
                requestParams.put("sign_type", signature+"RSA"+signature);
                String returnJson=JsonUtil.mapToJsonNoSinganaure(requestParams);
                para.setPara(returnJson);
            }
        }catch (Exception e){
            return null;
        }
        return para;
    }

    @Override
    public String callBack(HttpServletRequest request){
        boolean result = getPayfinishHandler(request);
        String pay_order_id = "", realPayMoney = "",tradeNo="",dataValue = "";
        if (result) {
            if (request.getParameter("out_trade_no") != null) {
                pay_order_id = request.getParameter("out_trade_no");
            }
            if (request.getParameter("total_fee") != null) {
                realPayMoney = request.getParameter("total_fee");
            }
            if(request.getParameter("trade_no") != null){
                tradeNo =  request.getParameter("trade_no");
            }
            if(request.getParameter("buyer_id") != null) {
                dataValue = request.getParameter("buyer_id");
            }
            String tradeStatus = request.getParameter("trade_status");
            if("TRADE_FINISHED".equals(tradeStatus)){
                return "SUCCSE";
            }
        }
        return "fail";
    }

    private boolean getPayfinishHandler(HttpServletRequest request){
        boolean flag = false;
        String merId = "";
        Map params = new HashMap();
        String sign=request.getParameter("sign");
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                if( "seller_id".equals(name) ){
                    merId = values[i];
                }
            }
            if(name.equals("sign")&&valueStr.contains(" ")){//此处用于get方式测试提交
                valueStr=valueStr.replaceAll(" ", "+");
            }
            params.put(name, valueStr);
        }
        if (AlipayNotify.verify(params)) {
            flag=AlipayAppUtil.checkTradeSucAndFin(request.getParameter("trade_status"));
        }
        return flag;
    }


}
