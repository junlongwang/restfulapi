package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum UseStatus {

    /**
     * 空闲
     */
    free(0),

    /**
     * 预约
     */
    subscribe(1),

    /**
     * 使用中
     */
    use(2);

    private int value;

    UseStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
