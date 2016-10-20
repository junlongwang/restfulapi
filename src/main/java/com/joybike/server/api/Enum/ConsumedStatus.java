package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum ConsumedStatus {

    /**
     * 完成
     */
    susuccess(0),

    /**
     * 退款
     */
    refund(1);


    private int value;

    ConsumedStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
