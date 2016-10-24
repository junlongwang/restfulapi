package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * Created by LongZiyuan on 2016/10/21.
 */
public class MapUtils {

    public  static SortedMap<String,String> getSortedMap(HashMap<String,String> queryMap){
        SortedMap<String,String> signMap =new TreeMap<String,String>();//签名用到的
        if( queryMap != null && queryMap.size() > 0 ){
            Iterator iter = queryMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                signMap.put(key, val);
            }
        }
        return signMap;
    }
    /**
     * @time:2015-08-28
     * @description:通过map封装xml内容
     * @param queryMap
     * @return
     */
    public static String queryOrderContent(HashMap<String,String> queryMap){
        StringBuffer queryStr = new StringBuffer();
        queryStr.append("<xml>");
        if( queryMap != null && queryMap.size() > 0 ){
            Iterator iter = queryMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                queryStr.append("<").append(key).append(">");
                queryStr.append(val).append("</").append(key).append(">");
            }
        }
        queryStr.append("</xml>");
        return queryStr.toString();
    }

}
