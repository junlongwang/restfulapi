package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum DisposeStatus {

    /**
     * 未处理
     */
    untreated(0),

    /**
     * 已处理
     */
    processed(1)
    ;

    private int value;

    DisposeStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
