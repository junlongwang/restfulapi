package com.joybike.server.api.ThirdPayService.impl;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.WxNotifyOrder;
import com.joybike.server.api.thirdparty.wxtenpay.PackageRequestHandler;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.security.*;
import java.util.*;
import java.util.regex.Pattern;

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
    private String keypath = WxPublicConstructUrlImpl.class.getResource("/apiclient_cert_1401808502.p12").getFile();
    private String notifyUrl = "http://api.joybike.com.cn/restful/pay/paynotify";
    private String wxRefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    private final Logger logger = Logger.getLogger(WxPublicConstructUrlImpl.class);
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
            map.put("body", payOrder.getPruductDesc());//随机字符串
            map.put("out_trade_no", payOrder.getId().toString());//商户订单号
            Double fMoney = (Double.valueOf(String.valueOf(payOrder.getOrderMoney())) * 100);
            BigDecimal total_fee = new BigDecimal(fMoney);
            map.put("total_fee",String.valueOf(total_fee));//总金额
            String spbillCreateIp = payOrder.getOperIP();
            if( StringUtil.isEmpty(spbillCreateIp) || "null".equals(spbillCreateIp) ) spbillCreateIp = "127.0.0.1";
            map.put("spbill_create_ip", "127.0.0.1"); //终端IP
            map.put("notify_url",notifyUrl);//通知地址. PayConfig.PAY_NOTIFY_URL
            map.put("trade_type", "JSAPI");//交易类型
            map.put("openid", payOrder.getOpenid());//openid
            String sign=SignUtil.sign(map,key).toUpperCase();
            map.put("sign", sign);//签名
            String xml=getXmlParam(map);//转化为xml格
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
                    signMap.put("paySign", paySign);
                    redirectParam.setMethod("POST");
                    redirectParam.setPara(JsonUtil.mapToJson(signMap));
                }
            }
        }catch(Exception e){
            return null;
        }
        return redirectParam;
    }

    private String getXmlParam(Map map){
        StringBuffer strXml = new StringBuffer();
        strXml.append("<xml>");
        strXml.append("<appid>").append(appid).append("</appid>");
        if(map.get("attach") != null && map.get("attach") != ""){
            strXml.append("<attach>").append(map.get("attach")).append("</attach>");
        }
        strXml.append("<body>").append(map.get("body")).append("</body>");
        strXml.append("<mch_id>").append(map.get("mch_id")).append("</mch_id>");
        strXml.append("<nonce_str>").append(map.get("nonce_str")).append("</nonce_str>");
        strXml.append("<notify_url>").append(map.get("notify_url")).append("</notify_url>");
        strXml.append("<openid>").append(map.get("openid")).append("</openid>");
        strXml.append("<out_trade_no>").append(map.get("out_trade_no")).append("</out_trade_no>");
        strXml.append("<spbill_create_ip>").append(map.get("spbill_create_ip")).append("</spbill_create_ip>");
        strXml.append("<total_fee>").append(map.get("total_fee")).append("</total_fee>");
        strXml.append("<trade_type>").append(map.get("trade_type")).append("</trade_type>");
        strXml.append("<sign>").append(map.get("sign")).append("</sign>");
        strXml.append("</xml>");
        return strXml.toString();
    }

    @Override
    public String callBack(WxNotifyOrder wxNotifyOrder) {
        String resultMsg="";
        if (wxNotifyOrder == null) {
            return resultMsg;
        }
        String reqSign = wxNotifyOrder.getSign();
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
        String sign = SignUtil.sign(hashmap,key).toUpperCase();
        if (!sign.equals(reqSign)) {
            return resultMsg;
        }
        String result = String.valueOf(hashmap.get("result_code"));
        if ("SUCCESS".equals(result)) {// 如果支付成功返回支付系统唯一订单号及支付金额
            String noOrder = String.valueOf(hashmap.get("out_trade_no"));
            String totalFee = String.valueOf(hashmap.get("total_fee"));
            Double realTotalFee = (Double) (Double.parseDouble(totalFee) / 100);
            String appId=String.valueOf(hashmap.get("appid"));
            String openId=String.valueOf(hashmap.get("openid"));
            String bank_type=String.valueOf(hashmap.get("bank_type"));
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

    public static void main(String[] args) {

    }
}


