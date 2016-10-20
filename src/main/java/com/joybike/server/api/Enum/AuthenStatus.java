package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum AuthenStatus {

    /**
     * 未认证
     */
    noCertified(0),

    certified(1);
    private int value;

    AuthenStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
