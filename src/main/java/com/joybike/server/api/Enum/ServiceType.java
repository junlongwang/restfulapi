package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/19.
 */
public enum ServiceType {

    master(0);


    private int value;

    ServiceType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
