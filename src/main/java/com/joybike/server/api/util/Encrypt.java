package com.joybike.server.api.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * Created by 58 on 2016/10/19.
 */
public class Encrypt {

    private static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(Charset.forName("UTF-8")), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(Charset.forName("UTF-8")));
        } catch (GeneralSecurityException gse) {
            //String msg=getStringFromException(gse);
            throw new IOException(gse.getMessage());

        }
        return bytes;
    }

    private static byte[] encryptMD5(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(Charset.forName("UTF-8")));
        } catch (GeneralSecurityException gse) {
            //String msg=getStringFromException(gse);
            //throw new IOException(msg);
            throw new IOException(gse.getMessage());
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static void main(String[] args) {
        try {
            System.out.println(encryptMD5("hello"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
