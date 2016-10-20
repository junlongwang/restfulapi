package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum ArousalType {

    /**
     * 电话或短信
     */
    remind(0),

    /**
     * 震动
     */
    shock(1)
    ;

    private int value;

    ArousalType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
