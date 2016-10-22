package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * Created by LongZiyuan on 2016/10/21.
 */
public class StringUtil {
    public static String urlDecode(String content, String charset) {
        try {
            if(content!=null){
                return URLDecoder.decode(content, charset);
            }else {
                return "";
            }
        } catch (UnsupportedEncodingException ex) {
            return content;
        }
    }

    public static String urlEncode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException ex) {
            return content;
        }
    }

    public static String urlEncode(String content) {
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            return content;
        }
    }


    public static String encode(String source, String from, String to) {
        byte[] bts;
        try {
            bts = source.getBytes(from);
            return new String(bts, to);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseCharacterEncoding(String contentType) {
        if (contentType == null)
            return (null);
        int start = contentType.indexOf("charset=");
        if (start < 0)
            return (null);
        String encoding = contentType.substring(start + 8);
        int end = encoding.indexOf(';');
        if (end >= 0)
            encoding = encoding.substring(0, end);
        encoding = encoding.trim();
        if ((encoding.length() > 2) && (encoding.startsWith("\""))
                && (encoding.endsWith("\"")))
            encoding = encoding.substring(1, encoding.length() - 1);
        return (encoding.trim());

    }

    public static boolean isEmpty(String str) {
        return (str == null) || (str.equals("")) || ("null".equals(str)) ;
    }

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String toHexString(byte[] buf) {
        return toHexString(buf, null, Integer.MAX_VALUE);
    }

    public static String toHexString(byte[] buf, String sep, int lineLen) {
        if (buf == null)
            return null;
        if (lineLen <= 0)
            lineLen = Integer.MAX_VALUE;
        StringBuffer res = new StringBuffer(buf.length * 2);
        for (int i = 0; i < buf.length; i++) {
            int b = buf[i];
            res.append(HEX_DIGITS[(b >> 4) & 0xf]);
            res.append(HEX_DIGITS[b & 0xf]);
            if (i > 0 && (i % lineLen) == 0)
                res.append('\n');
            else if (sep != null && i < lineLen - 1)
                res.append(sep);
        }
        return res.toString();
    }

    private static final int charToNibble(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'a' && c <= 'f') {
            return 0xa + (c - 'a');
        } else if (c >= 'A' && c <= 'F') {
            return 0xA + (c - 'A');
        } else {
            return -1;
        }
    }

    public static byte[] fromHexString(String text) {
        text = text.trim();
        if (text.length() % 2 != 0)
            text = "0" + text;
        int resLen = text.length() / 2;
        int loNibble, hiNibble;
        byte[] res = new byte[resLen];
        for (int i = 0; i < resLen; i++) {
            int j = i << 1;
            hiNibble = charToNibble(text.charAt(j));
            loNibble = charToNibble(text.charAt(j + 1));
            if (loNibble == -1 || hiNibble == -1)
                return null;
            res[i] = (byte) (hiNibble << 4 | loNibble);
        }
        return res;
    }

    public static String join(String[] arr, String split) {
        if (arr != null && arr.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : arr) {
                stringBuilder.append(s).append(split);
            }
            stringBuilder.delete(stringBuilder.length() - split.length(),
                    stringBuilder.length());
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

    public static String join(Object[] arr, String split) {
        if (arr != null && arr.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Object s : arr) {
                stringBuilder.append(String.valueOf(s)).append(split);
            }
            stringBuilder.delete(stringBuilder.length() - split.length(),
                    stringBuilder.length());
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

    public static Properties parseProperties(String data , String split){
        Properties properties = new Properties();
        if(!StringUtil.isEmpty(split) && !StringUtil.isEmpty(data)){
            for (String dd : data.split(split)) {
                String[] d = dd.split("=");
                if(d.length==2){
                    properties.put(d[0], d[1]);
                }
            }
        }
        return properties;
    }

    public static String splitAndFilterString(String input) {
        if (input == null || input.trim().equals("")) {
            return "";
        }

        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        return str;

    }
    /**
     * @time:2012-06-01
     * @description:String str:判断字符串内容,
     * @param str
     * @return boolean false:不为空，true:为空
     */
    public static boolean isNullOrEmpty(String str){
        boolean result =false;
        if( str == null || "null".equals(str) || "".equals(str) ){
            result =true;
        }
        return result;
    }

    /**
     * @time:2012-06-01
     * @description:String str:判断字符串内容,
     * @param str
     * @return boolean false:不为空，true:为空
     */
    public static boolean isNullOrTrimempty(String str){
        boolean result =false;
        if( str == null || "null".equals(str) || "".equals(str) || "".equals(str.trim())){
            result =true;
        }
        return result;
    }

    /**
     * 将map转化为字符串
     * @param paraMap
     * @return
     */
    public static String mapToString(Map paraMap){
        String returnStr="";
        if (paraMap != null && paraMap.keySet() != null
                && paraMap.keySet().size() > 0) {
            Iterator<String> iterator = paraMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = iterator.next();
                value = (String)paraMap.get(key);
                if(!isNullOrEmpty(value)){
                    returnStr+="&"+key+"="+value;
                }
            }
            if(!isNullOrEmpty(returnStr)){
                returnStr=returnStr.substring(1);
            }
        }
        return returnStr;
    }
}
