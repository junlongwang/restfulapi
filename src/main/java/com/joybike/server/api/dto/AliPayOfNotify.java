package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lishaoyong on 16/11/2.
 */
public class AliPayOfNotify {

    /**
     * 通知时间
     */
    @JSONField(ordinal = 1)
    private int notify_time;

    /**
     * 通知类型
     */
    @JSONField(ordinal = 2)
    private String notify_type;

    /**
     * 通知校验ID
     */
    @JSONField(ordinal = 3)
    private String notify_id;

    /**
     * 支付宝分配给开发者的应用Id
     */
    @JSONField(ordinal = 4)
    private String app_id;

    /**
     * 签名类型
     */
    @JSONField(ordinal = 5)
    private String sign_type;

    /**
     * 签名
     */
    @JSONField(ordinal = 6)
    private String sign;

    /**
     * 支付宝交易号
     */
    @JSONField(ordinal = 7)
    private String trade_no;

    /**
     * 商户订单号
     */
    @JSONField(ordinal = 8)
    private String out_trade_no;

    /**
     * 交易状态
     * WAIT_BUYER_PAY	交易创建，等待买家付款
     * TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
     * TRADE_SUCCESS	交易支付成功
     * TRADE_FINISHED	交易结束，不可退款
     */
    @JSONField(ordinal = 9)
    private String trade_status;

    /**
     * 订单金额
     */
    @JSONField(ordinal = 10)
    private BigDecimal total_amount;

    /**
     * 交易付款时间
     */
    @JSONField(ordinal = 11)
    private int gmt_payment;

    public AliPayOfNotify() {

    }

    @Override
    public String toString() {
        return "AliPayOfNotify{" +
                "notify_time=" + notify_time +
                ", notify_type='" + notify_type + '\'' +
                ", notify_id='" + notify_id + '\'' +
                ", app_id='" + app_id + '\'' +
                ", sign_type=" + sign_type + '\'' +
                ", sign='" + sign + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", trade_status='" + trade_status + '\'' +
                ", total_amount=" + total_amount +
                ", gmt_payment=" + gmt_payment +
                '}';
    }


    public AliPayOfNotify(int notify_time, String notify_type, String notify_id, String app_id, String sign_type, String sign, String trade_no, String out_trade_no, String trade_status, BigDecimal total_amount, int gmt_payment) {
        this.notify_time = notify_time;
        this.notify_type = notify_type;
        this.notify_id = notify_id;
        this.app_id = app_id;
        this.sign_type = sign_type;
        this.sign = sign;
        this.trade_no = trade_no;
        this.out_trade_no = out_trade_no;
        this.trade_status = trade_status;
        this.total_amount = total_amount;
        this.gmt_payment = gmt_payment;
    }

    public int getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(int notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public int getGmt_payment() {
        return gmt_payment;
    }

    public void setGmt_payment(int gmt_payment) {
        this.gmt_payment = gmt_payment;
    }
}
