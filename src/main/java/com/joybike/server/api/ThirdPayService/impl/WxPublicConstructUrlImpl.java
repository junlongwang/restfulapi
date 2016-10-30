package com.joybike.server.api.ThirdPayService.impl;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by LongZiyuan on 2016/10/23.
 */
@Service
public class WxPublicConstructUrlImpl implements WxPublicConstructUrlInter {

    @Value("#{thirdparty}")
    private Properties thirdpartyProperty;

    private String wxPreUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private String mch_id = "1401808502";
    private String appid = "wxa8d72207b41a315e";
    private String key = "BBFE4D6275760AB175F9385AD7710A70";
    private String notifyUrl = "http://api.joybike.com.cn/pay/paynotify";
    private String wxRefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

//    public WxPublicConstructUrlImpl()
//    {
//        wxPreUrl = thirdpartyProperty.getProperty("wxPreUrl");
//        mch_id = thirdpartyProperty.getProperty("wxPreUrl");
//        appid = thirdpartyProperty.getProperty("wxPublicAppid");
//        key = thirdpartyProperty.getProperty("wxPublicKey");
//        notifyUrl = thirdpartyProperty.getProperty("wxNotifyUrl");
//
//    }

    @Override
    public RedirectParam getUrl(ThirdPayBean payOrder){
        if(payOrder == null)
            return null;
        RedirectParam  redirectParam = new RedirectParam();
        try {
            Map<String,String> map = new HashMap();
            map.put("appid",appid);//公众账号ID
            map.put("mch_id",mch_id);//商户号
            map.put("nonce_str", WXUtil.getNonceStr());//随机字符串
            if(payOrder.getCosumeid() != null){
                map.put("attach",String.valueOf(payOrder.getCosumeid()));//附加数据
            }
            map.put("out_trade_no", payOrder.getId().toString());//商户订单号
            Double fMoney = (Double.valueOf(String.valueOf(payOrder.getOrderMoney())) * 100);
            BigDecimal total_fee = new BigDecimal(fMoney);
            map.put("total_fee",String.valueOf(total_fee));//总金额
            String spbillCreateIp = payOrder.getOperIP();
            if( StringUtil.isEmpty(spbillCreateIp) || "null".equals(spbillCreateIp) ) spbillCreateIp = "127.0.0.1";
            map.put("spbill_create_ip", spbillCreateIp); //终端IP
            map.put("notify_url",notifyUrl);//通知地址. PayConfig.PAY_NOTIFY_URL
            map.put("trade_type", "JSAPI");//交易类型
            map.put("openid", payOrder.getOpenid());//openid
            String sign=SignUtil.sign(map,key).toUpperCase();
            map.put("sign", sign);//签名
            String xml=ParseXml.parseXML(map);//转化为xml格式
            String httpType = "SSL";
            String timeOut = "60000";
            String res = HttpRequestSimple.sendHttpMsg(wxPreUrl, xml, httpType, timeOut);
            HashMap resMap=ParseXml.parseXml(res);
            if(resMap.get("return_code").equals("SUCCESS")){
                String reqSign=String.valueOf(resMap.get("sign"));
                String resSign=SignUtil.sign(resMap, key).toUpperCase();
                if(reqSign.equals(resSign)){
                    String   prepayId=String.valueOf(resMap.get("prepay_id"));

                    //生成paySign參數值
                    String timeStamp = WXUtil.getTimeStamp();
                    String nonceStr = WXUtil.getNonceStr();
                    String packageValue="prepay_id="+prepayId;
                    String signType="MD5";
                    Map<String, String> signMap = new HashMap<String, String>();
                    signMap.put("appId", appid);
                    signMap.put("nonceStr", nonceStr);
                    signMap.put("package", packageValue);
                    signMap.put("timeStamp", timeStamp);
                    signMap.put("signType", signType);
                    String paySign =SignUtil.sign(signMap,key).toUpperCase();

                    StringBuffer str = new StringBuffer();
                    str.append("\"appId\" : \"").append(appid).append("\"").append(",\n"); //公众号名称，由商户传入
                    str.append("\"timeStamp\" : \"").append(timeStamp).append("\"").append(",\n"); //时间戳
                    str.append("\"nonceStr\" : \"").append(nonceStr).append("\"").append(",\n"); //随机串
                    str.append("\"package\" : \"").append(packageValue).append("\"").append(",\n");//扩展包
                    str.append("\"signType\" : \""+signType+"\",\n"); //微信签名方式:1.sha1
                    str.append("\"paySign\" : \"").append(paySign).append("\""); //微信签名

                    redirectParam.setMethod("POST");
                    redirectParam.setPara(str.toString());
                }
            }
        }catch(Exception e){
            return null;
        }
        return redirectParam;
    }

    @Override
    public String callBack(HttpServletRequest request) {
        Map paraMap = ReadRequestUtil.getRequestMap(request);
        String resultMsg="";
        if (paraMap == null || paraMap.size() == 0) {
            return resultMsg;
        }
        String reqSign = String.valueOf(paraMap.get("sign"));
        String sign = SignUtil.sign(paraMap,key).toUpperCase();
        if (!sign.equals(reqSign)) {
            return resultMsg;
        }
        String result = String.valueOf(paraMap.get("result_code"));
        if ("SUCCESS".equals(result)) {// 如果支付成功返回支付系统唯一订单号及支付金额
            String noOrder = String.valueOf(paraMap.get("out_trade_no"));
            String totalFee = String.valueOf(paraMap.get("total_fee"));
            Double realTotalFee = (Double) (Double.parseDouble(totalFee) / 100);
            String appId=String.valueOf(paraMap.get("appid"));
            String openId=String.valueOf(paraMap.get("openid"));
            String bank_type=String.valueOf(paraMap.get("bank_type"));
            resultMsg = "success";
            return resultMsg;
        }
        return resultMsg;
    }

    @Override
    public String getRefundUrl(ThirdPayBean payOrder){
        String result = "fail";
        if (payOrder == null){
            return result;
        }
        try {
            Map<String,String> map = new HashMap();
            map.put("appid",appid);//公众账号ID
            map.put("mch_id",mch_id);//商户号
            map.put("nonce_str", WXUtil.getNonceStr());//随机字符串
//            if(String.valueOf(payOrder.getCosumeid()) != null && String.valueOf(payOrder.getCosumeid()) != ""){
//                map.put("attach",String.valueOf(payOrder.getCosumeid()));//附加数据
//            }
            map.put("out_trade_no", payOrder.getId().toString());//商户订单号
            map.put("transaction_id",payOrder.getTransaction_id());//微信支付订单号
            map.put("out_refund_no",payOrder.getRefundid().toString());//商户退款订单号
            map.put("body",payOrder.getPruductDesc());
            Double fMoney = (Double.valueOf(String.valueOf(payOrder.getOrderMoney())) * 100);
            BigDecimal total_fee = new BigDecimal(fMoney);
            map.put("total_fee",String.valueOf(total_fee));//总金额
            map.put("refund_fee",String.valueOf(total_fee));//总金额
            map.put("op_user_id",mch_id);//总金额
            String sign=SignUtil.sign(map,key).toUpperCase();
            map.put("sign", sign);//签名
            String xml=ParseXml.parseXML(map);//转化为xml格式
            String httpType = "SSL";
            String timeOut = "60000";
            String res = HttpRequestSimple.sendHttpMsg(wxRefundUrl, xml, httpType, timeOut);
            HashMap resMap=ParseXml.parseXml(res);
            if(resMap.get("return_code").equals("SUCCESS")){
                String reqSign=String.valueOf(resMap.get("sign"));
                String resSign=SignUtil.sign(resMap, key).toUpperCase();
                if(reqSign.equals(resSign)){
                    if (resMap.get("out_trade_no") == map.get("out_trade_no") && resMap.get("out_refund_no") == map.get("out_refund_no") &&resMap.get("transaction_id") == map.get("transaction_id")){
                        result = "SUCCSE";
                    }
                }
            }
        }catch (Exception e){
            return result;
        }
        return result;
    }

    public static void main(String[] args) {

    }
}


