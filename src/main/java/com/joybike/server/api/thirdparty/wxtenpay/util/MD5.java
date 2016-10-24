package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.security.*;
/**
 * Created by 58 on 2016/10/23.
 */
public class MD5 {
    private static char hexs[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f' };

    public static String encode(String source) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest digester = MessageDigest.getInstance("MD5");

            byte[] sbs = source.getBytes("UTF8");
            // 使用指定的字节更新摘要
            digester.update(sbs);
            byte[] rbs = digester.digest();
            //把密文转换成十六进制的字符串形式
            int j = rbs.length;
            char result[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = rbs[i];
                result[k++] = hexs[b >>> 4 & 0xf];
                result[k++] = hexs[b & 0xf];
            }
            return new String(result);
        } catch (Exception e) {
            return null;
        }
    }
}
