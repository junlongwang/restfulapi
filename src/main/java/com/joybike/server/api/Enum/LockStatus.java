package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum LockStatus {

    /**
     * 锁定
     */
    lock(0),

    /**
     * 开启
     */
    open(1)
    ;

    private int value;

    LockStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
