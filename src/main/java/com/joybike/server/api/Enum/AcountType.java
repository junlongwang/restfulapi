package com.joybike.server.api.Enum;

/**
 * Created by 58 on 2016/10/16.
 */
public enum AcountType {

    /**
     * 押金
     */
    deposit(-1),
    /**
     * 现金余额
     */
    cash(0),

    /**
     * 赠送优惠
     */
    balance(1);

    private int value;

    AcountType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }


}
