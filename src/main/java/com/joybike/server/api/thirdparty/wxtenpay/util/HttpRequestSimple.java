package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.io.UnsupportedEncodingException;
/**
 * Created by LongZiyuan on 2016/10/24.
 */
public class HttpRequestSimple {
    public static final String HTTP_TYPE = "SSL";
    public static final String TIME_OUT  = "60000";

    public static String sendHttpMsg(String URL, String strMsg, String httpType, String timeOut) {
        String returnMsg = "";
        CPHttpConnection httpSend = null;
        if (httpType.equals("SSL")) {
            httpSend = new HttpSSL(URL, timeOut);
        } else {
            httpSend = new Http(URL, timeOut);
        }
        // 设置获得响应结果的限制
        httpSend.setLenType(0);
        // 设置字符编码
        httpSend.setMsgEncoding("UTF-8");
        int returnCode = httpSend.sendMsg(strMsg);
        if (returnCode == 1) {
            try {
                returnMsg = new String(httpSend.getReceiveData(), "UTF-8").trim();
                //log.info("接收到响应报文,returnMsg=[" + returnMsg + "]");
            } catch (UnsupportedEncodingException e) {
                returnMsg = null;
            }
        } else {
            returnMsg = null;
        }
        return returnMsg;
    }
}
