package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum DealType {


    /**
     * 充值
     */
    deposit(1),

    /**
     * 消费
     */
    consumed(2),

    /**
     * 退消费
     */
    refundConsumed(3),

    /**
     * 退充值 外退
     */
    refund(4);

    private int value;

    DealType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
