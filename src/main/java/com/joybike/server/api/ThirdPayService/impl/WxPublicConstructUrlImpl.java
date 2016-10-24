package com.joybike.server.api.ThirdPayService.impl;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by LongZiyuan on 2016/10/23.
 */
public class WxPublicConstructUrlImpl implements WxPublicConstructUrlInter {
    private static String wxPreUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static String mch_id = "1401808502";
    private static String appid = "longziyuan";
    private static String key = "853D02D2F946329243B006C933A12E65";

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
            map.put("body", payOrder.getPruductDesc());//商品描述
            map.put("attach","附加数据原样返回");//附加数据
            map.put("out_trade_no", payOrder.getId().toString());//商户订单号
            Double fMoney = (Double.valueOf(String.valueOf(payOrder.getOrderMoney())) * 100);
            BigDecimal total_fee = new BigDecimal(fMoney);
            map.put("total_fee",String.valueOf(total_fee));//总金额
            String spbillCreateIp = payOrder.getOperIP();
            if( StringUtil.isEmpty(spbillCreateIp) || "null".equals(spbillCreateIp) ) spbillCreateIp = "127.0.0.1";
            map.put("spbill_create_ip", spbillCreateIp); //终端IP
            map.put("notify_url",payOrder.getNotifyUrl());//通知地址. PayConfig.PAY_NOTIFY_URL
            map.put("trade_type", "JSAPI");//交易类型
            map.put("openid", payOrder.getOpenid());//openid
            String sign=SignUtil.sign(map,key).toUpperCase();
            map.put("sign", sign);//签名
            String xml=ParseXml.parseXML(map);//转化为xml格式
            String httpType = "SSL";
            String timeOut = "60000";
            String res = HttpRequestSimple.sendHttpMsg("www.weixinpay.com", xml, httpType, timeOut);
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
        return null;
    }
}


