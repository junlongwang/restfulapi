package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum SecurityStatus {

    /**
     * 没有押金或已退款
     */
    noPaid(0),

    /**
     * 正常,已充值押金
     */
    normal(1),

    /**
     * 退款中
     */
    refundIng(2);


    private int value;

    SecurityStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
