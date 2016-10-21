package com.joybike.server.api.thirdparty.huaxinSdk;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public abstract class HuaXinUtils {
    private HuaXinUtils() {
    }

    public static String signTopRequestNew(RequestParametersHolder requestHolder, String secret, boolean isHmac) throws IOException {
        TreeMap sortedParams = new TreeMap();
        HuaXinMap appParams = requestHolder.getApplicationParams();
        if(appParams != null && appParams.size() > 0) {
            sortedParams.putAll(appParams);
        }

        HuaXinMap protocalMustParams = requestHolder.getProtocalMustParams();
        if(protocalMustParams != null && protocalMustParams.size() > 0) {
            sortedParams.putAll(protocalMustParams);
        }

        HuaXinMap protocalOptParams = requestHolder.getProtocalOptParams();
        if(protocalOptParams != null && protocalOptParams.size() > 0) {
            sortedParams.putAll(protocalOptParams);
        }

        Set paramSet = sortedParams.entrySet();
        StringBuilder query = new StringBuilder();
        if(!isHmac) {
            query.append(secret);
        }

        Iterator bytes = paramSet.iterator();

        while(bytes.hasNext()) {
            Entry bytes1 = (Entry)bytes.next();
            if(StringUtils.areNotEmpty(new String[]{(String)bytes1.getKey(), (String)bytes1.getValue()})) {
                query.append((String)bytes1.getKey()).append((String)bytes1.getValue());
            }
        }

        byte[] bytes11;
        if(isHmac) {
            bytes11 = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes11 = encryptMD5(query.toString());
        }

        return byte2hex(bytes11);
    }

    private static byte[] encryptHMAC(String data, String secret) throws IOException {
        Object bytes = null;

        try {
            SecretKeySpec var5 = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac msg1 = Mac.getInstance(var5.getAlgorithm());
            msg1.init(var5);
            byte[] bytes1 = msg1.doFinal(data.getBytes("UTF-8"));
            return bytes1;
        } catch (GeneralSecurityException var6) {
            String msg = getStringFromException(var6);
            throw new IOException(msg);
        }
    }

    private static String getStringFromException(Throwable e) {
        String result = "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        e.printStackTrace(ps);

        try {
            result = bos.toString("utf-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        return result;
    }

    private static byte[] encryptMD5(String data) throws IOException {
        Object bytes = null;

        try {
            MessageDigest var4 = MessageDigest.getInstance("MD5");
            byte[] msg1 = var4.digest(data.getBytes("UTF-8"));
            return msg1;
        } catch (GeneralSecurityException var41) {
            String msg = getStringFromException(var41);
            throw new IOException(msg);
        }
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if(hex.length() == 1) {
                sign.append("0");
            }

            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }
}
