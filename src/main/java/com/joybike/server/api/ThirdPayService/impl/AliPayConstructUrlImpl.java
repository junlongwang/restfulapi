package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import com.joybike.server.api.thirdparty.wxtenpay.util.RSASignature;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by LongZiyuan on 2016/10/24.
 */
public class AliPayConstructUrlImpl {

    private String partner = "";
    private String key = "";
    private String account = "";
    private String privateKey = "";
    private String publicKey = "";
    private String ALIPAY_PAY_SERVICE = "mobile.securitypay.pay";

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
                body=paraMap.containsKey("body")?String.valueOf(paraMap.get("body")):"Joybike账户充值";
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
                requestParams.put("notify_url",signature+paraMap.get("notify_url") + "?"
                        + "thirdType" + "=" + "alipayapp" + signature);
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
}
