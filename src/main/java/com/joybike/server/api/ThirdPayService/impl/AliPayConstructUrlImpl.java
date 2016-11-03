package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import com.joybike.server.api.thirdparty.wxtenpay.util.RSASignature;
import com.joybike.server.api.util.AlipayCore;
import com.joybike.server.api.util.RSA;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
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

    private final Logger logger = Logger.getLogger(AliPayConstructUrlImpl.class);

    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = "2088521096580226";
    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串
    public static String seller_id = partner;
    //支付宝帐号
    public static String ali_account = "wangliang@sktbj.com";
    // 收款支付宝账号，以2088开头由16位纯数字组成的字符串
    public static String app_id = "2016102202289143";//joybike
    // 商户的私钥
    public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALCjQgopUmAVdg8Ekie+7k/qu1PixS6OLJzoiYg2ZU/G90/pgHyNgCpPUNSRNOq3s2w2zOLtJMN1JN4x4aaGoTHYSgpghQr+QwOsuuogRKb0pLZkTnoh05NAfi1DX6dDnZ9/ooFXbWNhKeksE8eCiMKLoVasOwL6AN1RJ1fM748lAgMBAAECgYAItVPJKnZcNFKotOH7wacAG6N2pERyYiIC7lfxdjUSdM22i92Axn0eGOD0SeBg/gODf0Qkn+pjFIBnz++/BP4nYPY0CYoNOSO2S5f7v+xbtS4amC5nBWv5kxtcFvREhvsoPE9i7drUmKYkFRR/mmBklJIlFiBVHykPCO/bXcdBlQJBANx9EsigJK5r9PEQTq2z5GWA/66IEhicp1ldUq2k6uuowRJxzXQFLX3jzNg7lTA/eX0oxYjjL6sHCzZaqsygPmMCQQDNFi2SLq8SXF8M79RDuwUmpZfx/e6qc+5F+Yn7ChamGs3UdcZASVp0hxo//YpHgs/BKXDedPaba73ST3bqE07XAkBQifNJi426lL6lK6LBuntMRIGgvB14FgjfEMK5oQsax8q2tREqNxX17TcPKTyGojj7aeA1716jJ3CGCzpmgoYnAkEAspjNtq/Q5jxqyelRAGqtYapzV9m7LdUneuiEsIloj95nwM2PiAxZKYE96tvwv7W7FovwLsnMuCxrceqhs9Z8oQJAIcaccDaEhyZfJfTxOU4pIT0ctqDZ0rTZEWX4WwRdl83r60FoEbokqtrEoIGoa6Q//ClPkReDV7SljqvxYOgY9g==";
    // 支付宝的公钥，无需修改该
    public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static String Md5_key = "phetx8fnkr8obzc9gqfuxhbe4xj6no40";
    private String ALIPAY_PAY_SERVICE = "mobile.securitypay.pay";
    private String notifyUrl = "http://api.joybike.com.cn/restful/pay/paynotifyAli";
    @Override
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
                body=paraMap.containsKey("body")?String.valueOf(paraMap.get("body")):"测试数据";
                subject=paraMap.containsKey("subject")?String.valueOf(paraMap.get("subject")):"Joybike账户充值";
                totalFee=String.valueOf(paraMap.get("total_fee"));
                //客户端号，用于快登
//                if(paraMap.containsKey("app_id")&&(!StringUtil.isNullOrEmpty(String.valueOf(paraMap.get("app_id"))))){
//                    requestParams.put("app_id",signature+String.valueOf(paraMap.get("app_id"))+signature);
//                }
                requestParams.put("app_id",signature+app_id+signature);
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
//                if (paraMap.containsKey("show_url") && (!StringUtil.isNullOrEmpty(String.valueOf(paraMap.get("show_url"))))) {
//                    requestParams.put("show_url",signature+String.valueOf(paraMap.get("show_url"))+signature);
//                }
                requestParams.put("service",signature+ALIPAY_PAY_SERVICE+signature);
                requestParams.put("partner",signature+ partner +signature);
                requestParams.put("_input_charset",signature+"utf-8"+signature);
                //AlipayPropertiesConfigEnum.ALIPAY_NOTIFY_URL.getEnumValues()
                if (paraMap.get("consumeid") != null){
                    requestParams.put("notify_url",signature + notifyUrl + "?"
                            + "attach" + "=" + paraMap.get("consumeid") + signature);
                }
                else{
                    requestParams.put("notify_url",signature + notifyUrl +signature);
                }
                requestParams.put("out_trade_no",signature+outTradeNo+signature);
                requestParams.put("subject",signature+subject+signature);
                requestParams.put("body",signature+body+signature);
                requestParams.put("payment_type",signature+ "1" +signature);
                //requestParams.put("seller_id",signature+account+signature);//AlipayPropertiesConfigEnum.ALIPAY_SELLER_ID.getEnumValues()
                //requestParams.put("seller_id",signature+seller_id+signature);
                requestParams.put("seller_id",signature+seller_id+signature);
                requestParams.put("total_fee",signature+totalFee+signature);
                String preSignStr = AlipayAppUtil.createLinkString(requestParams);
                String sign = RSASignature.sign(preSignStr, private_key, "utf-8");
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
            logger.error("支付宝支付发生错误。",e);
            para.setPara(e.getMessage());
            return para;
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


    /**
     *
     * @param payBean
     * @return
     */
    @Override
    public String getRefundUrl(ThirdPayBean payBean){
//        String service = "refund_fastpay_by_platform_nopwd";
//        String notify_url = null;
//        String input_charset = "utf-8";
//        String sign_type = "MD5";
//        String refund_date = DateUtil.parse(new Date(), "yyyy-MM-dd hh:mm:ss");
//        String batch_no = DateUtil.parse(new Date(), "yyyyMMddhhmmss") + draw.getApplySourceId();
//        String batch_num = "1";
//        String detail_data = recharge.getTradeId() + "^" + draw.getDrawMoney()
//                + "^";
//        String paygateway = "https://mapi.alipay.com/gateway.do?";
//        String ItemUrl = Payment.CreateUrl(paygateway, input_charset, service,
//                partner, sign_type, batch_no, refund_date, batch_num,
//                detail_data, notify_url, privateKey,null);
//        log.info("refound.zfb:itemurl:" + ItemUrl);
//        String result = "";
//        try {
//            result = UrlUtil.doPostForStr(ItemUrl, null, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.info("原路提现(支付宝)：请求失败(" + ItemUrl + "),交易号("
//                    + recharge.getTradeId() + ")");
//            return "false";
//        }
//        log.info("原路提现(支付宝)：请求信息("
//                + ItemUrl
//                + ")，请求结果("
//                + result.replace("\r\n", "").replace("\n", "")
//                .replace("\r", "") + "),交易号(" + recharge.getTradeId()
//                + ")");
//        if (result.contains("<is_success>T</is_success>")) {
//            log.info("原路提现(支付宝)：请求成功,交易号(" + recharge.getTradeId() + ")");
//            return "true";
//        }
        return "false";
    }


    public RedirectParam getUrl1(HashMap<String,String> paraMap) {

        RedirectParam para = new RedirectParam();
        try {
//            String content = "\";
//            Map<String, String> paramMap = new HashMap<String, String>();
//            paramMap.put("service", content+"mobile.securitypay.pay"+content);
//            paramMap.put("partner", content+partner+content);
//            paramMap.put("_input_charset",content+ "utf-8"+content);
//            paramMap.put("sign_type", content+"RSA"+content);
//            paramMap.put("notify_url",content+ notifyUrl+content);
//            paramMap.put("app_id",content+ app_id+content);
//            //paramMap.put("appenv",content+ ""+content);
//            paramMap.put("out_trade_no", content+paraMap.get("out_trade_no")+content);
//            paramMap.put("subject", content+paraMap.get("subject")+content);
//            paramMap.put("payment_type", content+"1"+content);
//            paramMap.put("seller_id",content+ partner+content);
//            paramMap.put("total_fee", content+paraMap.get("total_fee")+content);
//            paramMap.put("body", content+paraMap.get("body")+content);
//            paramMap.put("it_b_pay", content+"30m"+content);
//            //paramMap.put("extern_token", content+""+content);
//            Map<String, String> signMap = AlipayCore.paraFilter(paramMap);
//            String srcData = AlipayCore.createLinkStringApp(signMap);
//            logger.info("Alipay sign srcData>>" + srcData);
//            String signData = RSA.sign(srcData, privateKey, "utf-8");
//            logger.info("Alipay sign signData>>" + signData);
//
//            signData= URLEncoder.encode(signData, "utf-8");
//            paramMap.put("sign", content+signData+content);
//
//            logger.info("给APP返回的DATA数据：" + paramMap.toString());
//
//            String returnJson=JsonUtil.mapToJsonNoSinganaure(paramMap);
//            para.setPara(returnJson);
        }catch (Exception e){
            logger.error("支付宝支付发生错误。",e);
            para.setPara(e.getMessage());
            return para;
        }
        return para;
    }
}
