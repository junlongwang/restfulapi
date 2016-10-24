package com.joybike.server.api.thirdparty.wxtenpay.util;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by LongZiyuan on 2016/10/24.
 */
public class ReadRequestUtil {
    /**
     * 读取request流
     *
     */
    public static String readReqStr(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(
                    request.getInputStream(), "utf-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {

            }
        }

        return sb.toString();
    }

    /**
     * 根据request取得参数集
     *
     * @param request
     * @return
     */
    public static Map getRequestMap(HttpServletRequest request) {
        Map paraMap = new HashMap();
        Enumeration nameEnums = request.getAttributeNames();
        while (nameEnums.hasMoreElements()) {
            String key = String.valueOf(nameEnums.nextElement());
            paraMap.put(key, request.getAttribute(key));
        }
        return paraMap;
    }
}
