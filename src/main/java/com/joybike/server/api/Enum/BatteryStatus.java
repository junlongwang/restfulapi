package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum BatteryStatus {

    /**
     * 充电
     */
    charge(0),

    /**
     * 放电
     */
    discharge(1)
    ;
    private int value;

    BatteryStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
