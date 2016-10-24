package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.ThirdPayService.appConstructUrlInter;
import org.springframework.stereotype.Service;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import com.joybike.server.api.thirdparty.wxtenpay.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by LongZiyuan on 2016/10/20.
 */
@Service
public class WxAppConstructUrlImpl implements appConstructUrlInter {

    private static String wxPreUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static String mch_id = "1401808502";
    private static String appid = "longziyuan";

    @Override
    public RedirectParam getUrl(HashMap<String, String> paraMap) {
        RedirectParam para = new RedirectParam();
        if(paraMap==null||paraMap.size()==0){
            return null;
        }
        String prepayId="";
        String returnJson="";
        try {
            prepayId = getPrePayIdNew(paraMap);
            returnJson=getWxAppJsonStr(prepayId,paraMap);
        } catch (Exception e) {
            return null;
        }
        para.setPara(returnJson);
        return para;
    }

    public static String getPrePayIdNew(HashMap<String,String> paraMap){
        String getPreIdContent="";
        try {
            getPreIdContent = getPreOrder(paraMap);
        } catch (Exception e) {
            return null;
        }
        PrepayIdRequestHandler prepayIdRequestHandler = new PrepayIdRequestHandler(
                null, null);
        prepayIdRequestHandler.setParameter("input_charset", "UTF-8");
        prepayIdRequestHandler.setGateUrl(wxPreUrl);
        String requestResultXml=prepayIdRequestHandler.sendPrepayNew(getPreIdContent);
        String prepayId = parseXmlPrePayId(requestResultXml);
        return prepayId;
    }

    public static String getPreOrder(HashMap<String,String> paraMap) {
        // 生成package参数值
        PackageRequestHandler packageReqHandler = new PackageRequestHandler(
                null, null);
        //2015年9月9日，在下面加密方法中获取key值 syh
//		packageReqHandler.setKey(WxAppPropertiesConfigEnum.WxAppAppParternKey.getEnumValues());
        packageReqHandler.setParameter("appid", appid);
        packageReqHandler.setParameter("mch_id",mch_id);
        packageReqHandler.setParameter("nonce_str", WXUtil.getNonceStr());
        packageReqHandler.setParameter("trade_type", "APP");
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
        packageReqHandler.setParameter("attach",paraMap.get("attach"));

        //获取签名信息
        String sign =getMd5Sign(packageReqHandler);
        String packageValue = getXmlParam(packageReqHandler, sign);
        return packageValue;
    }

    /**
     * @time:2015-08-19
     * @description:获取md5签名
     * @param packageReqHandler
     * @return
     */
    private static String getMd5Sign(PackageRequestHandler packageReqHandler){
        String appid = packageReqHandler.getParameter("appid");
        Set es = packageReqHandler.getAllParameters().entrySet();
        return WxDealUtil.getMd5SignPub(es,appid);
    }

    private static String getMd5Sign(PaySignRequestHandler paySignReqHandler){
        String appid = paySignReqHandler.getParameter("appid");
        Set es = paySignReqHandler.getAllParameters().entrySet();
        return WxDealUtil.getMd5SignPub(es,appid);
    }

    private static String getXmlParam(PackageRequestHandler packageReqHandler,String sign){
        StringBuffer strXml = new StringBuffer();
        String appid = packageReqHandler.getParameter("appid");
        strXml.append("<xml>");
        strXml.append("<appid>").append(appid).append("</appid>");
        strXml.append("<attach>").append(packageReqHandler.getParameter("attach")).append("</attach>");
        strXml.append("<body>").append(packageReqHandler.getParameter("body")).append("</body>");
        strXml.append("<mch_id>").append(packageReqHandler.getParameter("mch_id")).append("</mch_id>");
        strXml.append("<nonce_str>").append(packageReqHandler.getParameter("nonce_str")).append("</nonce_str>");
        strXml.append("<notify_url>").append(packageReqHandler.getParameter("notify_url")).append("</notify_url>");
        strXml.append("<out_trade_no>").append(packageReqHandler.getParameter("out_trade_no")).append("</out_trade_no>");
        strXml.append("<spbill_create_ip>").append(packageReqHandler.getParameter("spbill_create_ip")).append("</spbill_create_ip>");
        strXml.append("<total_fee>").append(packageReqHandler.getParameter("total_fee")).append("</total_fee>");
        strXml.append("<trade_type>").append(packageReqHandler.getParameter("trade_type")).append("</trade_type>");
        strXml.append("<sign>").append(sign).append("</sign>");
        strXml.append("</xml>");
        return strXml.toString();
    }

    private static String parseXmlPrePayId(String requestResultXml){
        String prepayId = "";
        HashMap<String,String> doResultMap=ParseXml.parseXml(requestResultXml);
        if( doResultMap == null || doResultMap.size() == 0 ) return prepayId;

        String resultCode=doResultMap.get("result_code");
        String returnCode = doResultMap.get("return_code");
        String appid = doResultMap.get("appid");
        //签名验证
        String reqSign = doResultMap.get("sign");
        //移除sign
        doResultMap.remove("sign");
        String sign = WxDealUtil.getMd5SignPub(MapUtils.getSortedMap(doResultMap).entrySet(),appid);
        if( !StringUtil.isNullOrEmpty(reqSign) && reqSign.equals(sign) ){
            if( "SUCCESS".equals(resultCode) && "SUCCESS".equals(returnCode) ){
                prepayId =doResultMap.get("prepay_id");
            }
        }else{
            return null;
        }

        return prepayId;
    }

    private String getWxAppJsonStr(String prepayId,HashMap<String, String> paraMap) {
        String appid = paraMap.get("appid");
        String partnerId = mch_id;
        paraMap.put("prepayId", prepayId);
        paraMap.put("partnerid", partnerId);
        return getWxAppJsonPub(paraMap);
    }

    private String getWxAppJsonPub(HashMap<String, String> paraMap){
        String appid = paraMap.get("appid");
        String partnerId = paraMap.get("partnerid");
        String prepayId = paraMap.get("prepayId");
        String sign = "";
        //生成签名
        // 1、字符排序，&链接再 SHA1加密
        PaySignRequestHandler paySignReqHandler = new PaySignRequestHandler(null, null);
        paySignReqHandler.setParameter("appid",appid);
        paySignReqHandler.setParameter("appkey","密钥");
        paySignReqHandler.setParameter("package", "Sign=WXPay");
        paySignReqHandler.setParameter("partnerid",partnerId);
        paySignReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
        paySignReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
        paySignReqHandler.setParameter("prepayid", prepayId);
        sign=getMd5Sign(paySignReqHandler);
        //remove key
        paraMap.remove("partnerid");
        paraMap.remove("prepayId");
        paraMap.remove("sign");
        paySignReqHandler.setParameter("sign", sign);
        SortedMap map = paySignReqHandler.getAllParameters();
        map.remove("appkey");// 去掉key值
        return JsonUtil.mapToJson(map);
    }

    public static void main(String []str){
        HashMap<String, String> paraMap = new HashMap();
        WxAppConstructUrlImpl wxAppConstructUrl = new WxAppConstructUrlImpl();
        wxAppConstructUrl.getUrl(paraMap);
    }
}
