package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 * 消费状态
 */
public enum MoneyStatus {


    /**
     * 新建
     */
    initial(1),

    /**
     * 成功
     */
    success(3),

    /**
     * 未扣费
     */
    noSuccess(4),

    /**
     * 退款成功
     */
    refundSuccess(5);

    private int value;

    MoneyStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
