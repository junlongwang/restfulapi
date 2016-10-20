package com.joybike.server.api.util;

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
}
