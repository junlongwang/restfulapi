package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by lishaoyong on 16/11/1.
 */
public class AlipayDto  implements Serializable {

    /**
     * 商户订单号
     */
    @JSONField(ordinal = 1)
    private String out_trade_no;

    /**
     *客户端类型
     */
    @JSONField(ordinal = 2)
    private String appenv;

    /**
     * 	订单标题
     */
    @JSONField(ordinal = 3)
    private String subject;

    /**
     * 	订单总金额，单位为元，精确到小数点后两位，取值范围
     */
    @JSONField(ordinal = 4)
    private String total_fee;

    /**
     * 超时时间
     */
    @JSONField(ordinal = 5)
    private String it_b_pay;

    /**
     * 订单描述
     */
    @JSONField(ordinal = 6)
    private String body;

    public AlipayDto(String out_trade_no, String appenv, String subject, String total_fee, String it_b_pay, String body) {
        this.out_trade_no = out_trade_no;
        this.appenv = appenv;
        this.subject = subject;
        this.total_fee = total_fee;
        this.it_b_pay = it_b_pay;
        this.body = body;
    }
    public AlipayDto(){

    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAppenv() {
        return appenv;
    }

    public void setAppenv(String appenv) {
        this.appenv = appenv;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getIt_b_pay() {
        return it_b_pay;
    }

    public void setIt_b_pay(String it_b_pay) {
        this.it_b_pay = it_b_pay;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AlipayDto{" +
                "out_trade_no='" + out_trade_no + '\'' +
                ", appenv='" + appenv + '\'' +
                ", subject='" + subject + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", it_b_pay='" + it_b_pay + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}
