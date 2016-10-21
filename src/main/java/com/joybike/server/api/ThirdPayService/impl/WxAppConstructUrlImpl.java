package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.ThirdPayService.appConstructUrlInter;
import org.springframework.stereotype.Service;
import com.joybike.server.api.model.RedirectParam;

import java.util.HashMap;
/**
 * Created by LongZiyuan on 2016/10/20.
 */
@Service
public class WxAppConstructUrlImpl implements appConstructUrlInter {

    private static String wxPreUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Override
    public RedirectParam getUrl(HashMap<String, String> paraMap) {
        RedirectParam para = new RedirectParam();
        if(paraMap==null||paraMap.size()==0){
            return null;
        }
        String prepayId="";
        String returnJson="";
        try {
            //使用预支付订单哪个接口
            String appid = paraMap.get("appid");
            String paramFlag = WxConfigMap.getWxPreMethod(appid);
            prepayId = getPrePayIdNew(paraMap);
            returnJson=getWxAppJsonStr(prepayId,paraMap);
        } catch (Exception e) {
            return null;
        }
        para.setPara(returnJson);
        return para;
    }

    public static String getPrePayIdNew(HashMap<String,String> paraMap){
        String appid=paraMap.get("appid");
        String getPreIdContent="";
        try {
            getPreIdContent = getPreOrder(paraMap);
        } catch (Exception e) {
            return null;
        }
        return "";
    }

    public static String getPreOrder(HashMap<String,String> paraMap) {
        // 生成package参数值
        PackageRequestHandler packageReqHandler = new PackageRequestHandler(
                null, null);
        String appid = paraMap.get("appid");
        //2015年9月9日，在下面加密方法中获取key值 syh
//		packageReqHandler.setKey(WxAppPropertiesConfigEnum.WxAppAppParternKey.getEnumValues());
        packageReqHandler.setParameter("appid", appid);
        packageReqHandler.setParameter("mch_id",WxConfigMap.getWxMchId(appid));
        packageReqHandler.setParameter("nonce_str", WXUtil.getNonceStr());
        String tradeType = WxConfigMap.getWxTradeType(appid);
        if( "WX_APP_TRADE_TYPE".equals(tradeType) ) tradeType = WxAppPropertiesConfigEnum.WxAppTradeType.getEnumValues();
        packageReqHandler.setParameter("trade_type", tradeType);
        packageReqHandler.setParameter("out_trade_no",paraMap.get("out_trade_no"));
        //充值金额以分为单位
        Double dMoney = Double.valueOf(String.valueOf(paraMap.get("total_fee"))) * 100;
        BigDecimal total_fee =NumberFormateUtil.getdoubleRoundOne(dMoney);
        packageReqHandler.setParameter("total_fee",String.valueOf(total_fee));
        packageReqHandler.setParameter("notify_url", paraMap.get("notify_url"));
        packageReqHandler.setParameter("body",paraMap.get("body"));
        String spbill_create_ip=paraMap.get("spbill_create_ip");
        if(StringUtil.isNullOrEmpty(spbill_create_ip)){
            spbill_create_ip="127.0.0.1";
        }
        packageReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
        packageReqHandler.setParameter("attach",WxConfigMap.getWxAppChannelId(appid));// WxAppPropertiesConfigEnum.WxAppPayFlag.getEnumValues()

        //获取签名信息
        String sign =getMd5Sign(packageReqHandler);
        String packageValue = getXmlParam(packageReqHandler,sign);
        log.info(sign+"<<<sign>>>WxAppPayConstructUrlImpl.packageValue.xml:"+ packageValue);
        return packageValue;
    }
}
