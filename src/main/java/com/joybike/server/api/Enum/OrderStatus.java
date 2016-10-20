package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum OrderStatus {

    /**
     * 新建
     */
    newly(1),

    /**
     * 结束
     */
    end(2),

    /**
     * 支付完成
     */
    complete(15)
    ;
    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
