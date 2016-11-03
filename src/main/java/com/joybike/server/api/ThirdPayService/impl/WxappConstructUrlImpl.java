package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.ThirdPayService.WxappConstructUrlInter;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.WxNotifyOrder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import com.joybike.server.api.thirdparty.wxtenpay.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import java.io.*;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Pattern;

/**
 * Created by LongZiyuan on 2016/10/20.
 */
@Service
public class WxappConstructUrlImpl implements WxappConstructUrlInter {

    private String wxPreUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private String mch_id = "1404387302";
    private String appid = "wxbabc4e15389aff36";
    private String key = "F1BDA99703815CE223FF494A9039ADA3";
    private String notifyUrl = "http://api.joybike.com.cn/restful/pay/paynotify";
    private String wxRefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    private String keypath = WxappConstructUrlImpl.class.getResource("/apiclient_cert_1404387302.p12").getFile();

    private final Logger logger = Logger.getLogger(WxappConstructUrlImpl.class);
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
            returnJson = getWxAppJsonStr(prepayId,paraMap);
        } catch (Exception e) {
            return null;
        }
        para.setPara(returnJson);
        return para;
    }

    public String getPrePayIdNew(HashMap<String,String> paraMap){
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

    public String getPreOrder(HashMap<String,String> paraMap) {
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
        packageReqHandler.setParameter("body", paraMap.get("body"));
        //充值金额以分为单位
        Double dMoney = Double.valueOf(String.valueOf(paraMap.get("total_fee"))) * 100;
        BigDecimal total_fee =NumberFormateUtil.getdoubleRoundOne(dMoney);
        packageReqHandler.setParameter("total_fee",String.valueOf(total_fee));
        packageReqHandler.setParameter("notify_url", notifyUrl);
        String spbill_create_ip=paraMap.get("spbill_create_ip");
        if(StringUtil.isNullOrEmpty(spbill_create_ip)){
            spbill_create_ip="127.0.0.1";
        }
        packageReqHandler.setParameter("spbill_create_ip", spbill_create_ip);
        if(paraMap.get("attach") != null && paraMap.get("attach") != ""){
            packageReqHandler.setParameter("attach",paraMap.get("attach"));
        }

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
        if(packageReqHandler.getParameter("attach") != null && packageReqHandler.getParameter("attach") != ""){
            strXml.append("<attach>").append(packageReqHandler.getParameter("attach")).append("</attach>");
        }
        strXml.append("<mch_id>").append(packageReqHandler.getParameter("mch_id")).append("</mch_id>");
        strXml.append("<nonce_str>").append(packageReqHandler.getParameter("nonce_str")).append("</nonce_str>");
        strXml.append("<body>").append(packageReqHandler.getParameter("body")).append("</body>");
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
        String partnerId = mch_id;
        paraMap.put("prepayId", prepayId);
        paraMap.put("partnerid", partnerId);
        return getWxAppJsonPub(paraMap);
    }

    private String getWxAppJsonPub(HashMap<String, String> paraMap){
        String partnerId = paraMap.get("partnerid");
        String prepayId = paraMap.get("prepayId");
        String sign = "";
        //生成签名
        // 1、字符排序，&链接再 SHA1加密
        PaySignRequestHandler paySignReqHandler = new PaySignRequestHandler(null, null);
        paySignReqHandler.setParameter("appid",appid);
        //paySignReqHandler.setParameter("appkey",key);
        paySignReqHandler.setParameter("package", "Sign=WXPay");
        paySignReqHandler.setParameter("partnerid",partnerId);
        paySignReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
        paySignReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
        paySignReqHandler.setParameter("prepayid", prepayId);
        sign=getMd5Sign(paySignReqHandler);
        //remove key
        paraMap.remove("partnerid");
        //paraMap.remove("prepayId");
        paraMap.remove("sign");
        paySignReqHandler.setParameter("sign", sign);
        SortedMap map = paySignReqHandler.getAllParameters();
        map.remove("appkey");// 去掉key值
        return JsonUtil.mapToJson(map);
    }

    public static void main(String []str){
        HashMap<String, String> paraMap = new HashMap();
        WxappConstructUrlImpl wxAppConstructUrl = new WxappConstructUrlImpl();
        wxAppConstructUrl.getUrl(paraMap);
    }

    @Override
    public String callBack(WxNotifyOrder wxNotifyOrder){
        String resultMsg="";
        if (wxNotifyOrder != null){
            String returnCode = wxNotifyOrder.getReturn_code();
            String resultCode = wxNotifyOrder.getResult_code();
            if( "SUCCESS".equals(resultCode) && "SUCCESS".equals(returnCode) ){
                String signRequest = wxNotifyOrder.getSign();
                HashMap<String,String> hashmap = new HashMap<String,String>();
                hashmap.put("appid",wxNotifyOrder.getAppid());
                hashmap.put("attach",wxNotifyOrder.getAttach());
                hashmap.put("bank_type",wxNotifyOrder.getBank_type());
                hashmap.put("cash_fee",wxNotifyOrder.getCash_fee());
                hashmap.put("fee_type",wxNotifyOrder.getFee_type());
                hashmap.put("is_subscribe",wxNotifyOrder.getIs_subscribe());
                hashmap.put("mch_id",wxNotifyOrder.getMch_id());
                hashmap.put("nonce_str",wxNotifyOrder.getNonce_str());
                hashmap.put("openid",wxNotifyOrder.getOpenid());
                hashmap.put("out_trade_no",wxNotifyOrder.getOut_trade_no());
                hashmap.put("result_code",wxNotifyOrder.getResult_code());
                hashmap.put("return_code",wxNotifyOrder.getReturn_code());
                hashmap.put("time_end",wxNotifyOrder.getTime_end());
                hashmap.put("total_fee",wxNotifyOrder.getTotal_fee());
                hashmap.put("trade_type",wxNotifyOrder.getTrade_type());
                hashmap.put("transaction_id",wxNotifyOrder.getTransaction_id());
                //签名验证
                String sign = WxDealUtil.getMd5SignPub((MapUtils.getSortedMap(hashmap).entrySet()), wxNotifyOrder.getAppid());
                if( !StringUtil.isNullOrEmpty(signRequest) && signRequest.equals(sign) ){
                    String totalFee = hashmap.get("total_fee");//总金额
                    String outTradeNo = hashmap.get("out_trade_no");//商户订单号
                    String openId =hashmap.get("openid");
                    String appId=hashmap.get("appid");
                    Double realTotalFee = (Double) (Double.parseDouble(totalFee != null ? totalFee : "0") / 100) ;
                    resultMsg = "success";
                    return resultMsg;
                }else{
                    return resultMsg;
                }
            }
        }
        return resultMsg;
    }

    @Override
    public String getRefundUrl(ThirdPayBean payOrder){
        String result = "fail";
        if (payOrder == null){
            return result;
        }
        logger.info("微信退款（通用版）outRefundNo" + payOrder.getRefundid() +"------------ begin ----------------");
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            logger.error("获取微信app退款证书失败");
        }
        FileInputStream instream = null;
        try {
            instream = new FileInputStream(new File(keypath));
        } catch (FileNotFoundException e) {
            logger.error("获取证书文件失败");
        }
        try {
            keyStore.load(instream, mch_id.toCharArray());
        } catch (Exception e) {
            logger.error("注入证书密钥失败");
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                logger.error("关闭instream失败");
            }
        }

        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("appid", appid);
        paramsMap.put("mch_id", mch_id);
        paramsMap.put("nonce_str", WXUtil.getNonceStr());
        paramsMap.put("transaction_id", payOrder.getTransaction_id());
        paramsMap.put("out_trade_no", String.valueOf(payOrder.getCosumeid()));
        paramsMap.put("out_refund_no", String.valueOf(payOrder.getRefundid()));

        Double fMoney = (Double.valueOf(String.valueOf(payOrder.getOrderMoney())) * 100);
        BigDecimal total_fee = new BigDecimal(fMoney);
        paramsMap.put("total_fee", String.valueOf(total_fee));
        paramsMap.put("refund_fee", String.valueOf(total_fee));
        paramsMap.put("op_user_id", mch_id);
        String sign=SignUtil.sign(paramsMap,key).toUpperCase();
        paramsMap.put("sign", sign);
        String requestURL = wxRefundUrl;

        try {

            HttpPost httpPost = new HttpPost(requestURL);
            //添加参数
            StringEntity myEntity = new StringEntity(ParseXml.parseXML(paramsMap), "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(myEntity);

            logger.info("executing request" +  httpPost.getRequestLine() + paramsMap.toString());

            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                HttpEntity rspEntity = response.getEntity();
                logger.info("response status: " + response.getStatusLine());
                if (rspEntity != null) {
                    logger.info("Response content length: "
                            + rspEntity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(rspEntity.getContent()));
                    String text;
                    String refund_id = null;
                    String pattern = "<refund_id>.*?(\\d+)";
                    Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    while ((text = bufferedReader.readLine()) != null) {
                        if (text.contains("<result_code><![CDATA[SUCCESS]]>")) {

                            result = "success";
                            logger.info("原路提现(微信退款（通用版）)：成功,成功,成功");
                            break;
                            //TODO
                        }
                    }

                }
                consumeEntity(rspEntity);
            } catch (Exception e) {
                logger.error("微信退款（通用版）2",e);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            logger.error("微信退款（通用版）3",e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("微信退款（通用版）退款------------ end ----------------");
        return result;
    }

    private void consumeEntity(HttpEntity entity) {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            InputStream instream;
            try {
                instream = entity.getContent();
                if (instream != null) {
                    instream.close();
                }
            } catch (Exception e) {
                logger.error("consumeHttpEntity error", e);
            }

        }
    }
}
