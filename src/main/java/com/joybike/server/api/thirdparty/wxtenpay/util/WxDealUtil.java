package com.joybike.server.api.thirdparty.wxtenpay.util;
import com.alibaba.fastjson.JSONObject;
import com.joybike.server.api.thirdparty.wxtenpay.util.*;
import com.joybike.server.api.thirdparty.wxtenpay.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 58 on 2016/10/21.
 */
public class WxDealUtil {

    /**
     * @time:2015-08-19
     * @description:生成md5签名
     * @param es
     * @param appid
     * @return
     */
    public static String getMd5SignPub(Set es,String appid){
        StringBuffer sb  =new StringBuffer();
        for (Iterator it = es.iterator(); it.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (v != null && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k))
                sb.append((new StringBuilder(String.valueOf(k))).append("=")
                        .append(v).append("&").toString());
        }
        sb.append("key=").append("需要填写");
        String enc =  TenpayUtil.getCharacterEncoding(null,null);
        //签名信息
        String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
        return sign;
    }
}
