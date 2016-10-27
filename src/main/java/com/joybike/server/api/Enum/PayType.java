package com.joybike.server.api.Enum;

/**
 * Created by 58 on 2016/10/16.
 */
public enum PayType {
    /**
     * 微信
     */
    weixin(0),
    /**
     * 支付宝
     */
    Alipay(1),

    /**
     * 微信公众号支付
     */
    weixinpublic(2);

    private int value;

    PayType(int value) {
        this.value = value;
    }

    // 构造方法
    public int getValue() {
        return this.value;
    }
}
