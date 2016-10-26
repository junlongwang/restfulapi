package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.text.SimpleDateFormat;
import java.util.Random;
/**
 * Created by 58 on 2016/10/25.
 */
public class randomNoUtil {

    private String  getRandNo()
    {
        java.util.Date dateNow = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
        String dateNowStr = dateFormat.format(dateNow);
        StringBuffer sb = new StringBuffer(dateNowStr);
        int randNo = 0;
        randNo = new Random().nextInt(99999999 - 10000000 + 1) + 10000000;
        if (String.valueOf(randNo).length() == 8){
            dateNowStr = dateNowStr + String.valueOf(randNo);
            return dateNowStr;
        }
        return "";
    }

}
