package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/20.
 */
public enum RechargeType {

    /**
     *余额
     */
    balance(0),

    /**
     * 押金
     */
    deposit(1)
    ;

    private int value;

    RechargeType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
