package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum VehicleEnableType {

    /**
     * 启用
     */
    enable(0),

    /**
     * 禁止
     */
    prohibit(1),


    /**
     * 故障
     */
    fault(2)
    ;


    private int value;

    VehicleEnableType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
