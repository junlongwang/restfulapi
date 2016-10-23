package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/20.
 */
public enum SubscribeStatus {

    /**
     * 预约中
     */
    subscribe(0),

    /**
     * 使用中
     */
    use(2),
    ;

    private int value;

    SubscribeStatus(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }

}
