package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum  DepositStatus {
    /**
     * 新建
     */
    initial(1),

    /**
     * 成功
     */
    susuccess(2);


    private int value;

    DepositStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
