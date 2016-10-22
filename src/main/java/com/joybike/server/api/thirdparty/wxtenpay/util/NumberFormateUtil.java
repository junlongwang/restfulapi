package com.joybike.server.api.thirdparty.wxtenpay.util;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by LongZiyuan on 2016/10/21.
 * @description:对价格的格式化
 * @version $Id: NumberFormateUtil.java
 */
public class NumberFormateUtil {
    public static BigDecimal getdoubleRoundOne(double money){
        return new BigDecimal(money).setScale(0, BigDecimal.ROUND_HALF_UP);
    }
}
