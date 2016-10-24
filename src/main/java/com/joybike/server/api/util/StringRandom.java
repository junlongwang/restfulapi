package com.joybike.server.api.util;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by lishaoyong on 16/10/19.
 */
public class StringRandom {

    /**
     * 随时生成字符串
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();


        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    /**
     * 生成 14位的唯一orderCode
     *
     * @param orderId
     * @param userId
     * @return
     */
    public static String GenerateOrderCode(int orderId, long userId) {
        if (orderId < 1)
            return "";

        //var orderCreated = UnixTime.FromUnixTime(orderCreatedTime);

        //订单ID与int.MaxValue的绝对值，不足10位数则左边补0
        String codeBase = String.valueOf(Integer.MAX_VALUE - orderId);
        if (codeBase.length() < 10)
            codeBase = padLeft(codeBase, 10);
        char[] codeArray = codeBase.toCharArray();

        StringBuffer orderCode = new StringBuffer();
        //var month =  orderCreated.Month.ToString(CultureInfo.InvariantCulture).PadLeft(9, '2');//月份。当月数为一位数时，左边补“9”，5月："95"
        int max = 9;
        int min = 1;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        orderCode.append(codeArray[0]);
        orderCode.append(codeArray[1]);
        orderCode.append(codeArray[2]);
        orderCode.append(codeArray[3]);
        orderCode.append(userId % 10);//user_id除10余数
        orderCode.append(codeArray[4]);
        orderCode.append(codeArray[5]);
        orderCode.append(codeArray[6]);
        orderCode.append(codeArray[7]);
        orderCode.append(codeArray[8]);
        orderCode.append(codeArray[9]);
        max = 99;
        min = 10;
        orderCode.append(random.nextInt(max) % (max - min + 1) + min);

        return orderCode.toString();
    }

    public static String padLeft(String s, int length) {
        byte[] bs = new byte[length];
        byte[] ss = s.getBytes();
        Arrays.fill(bs, (byte) (48 & 0xff));
        System.arraycopy(ss, 0, bs, length - ss.length, ss.length);
        return new String(bs);
    }
}
