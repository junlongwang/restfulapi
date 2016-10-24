package com.joybike.server.api.model;

/**
 * Created by LongZiyuan on 2016/10/24.
 */
public class WxPayXML {
    private String appid;   //公众账号(APP)ID
    private String mch_id;  //商户号
    private String sub_appid;   //子商户公众账号ID
    private String sub_mch_id;  //子商户号
    private String device_info; //设备号
    private String nonce_str;   //随机字符串
    private String sign;    //签名
    private String body;    //商品描述
    private String detail;  //商品详情
    private String attach;  //附加数据
    private String out_trade_no;    //商户订单号
    private int fee_type;   //货币类型
    private String total_fee;   //总金额
    private String time_start;
    private String time_expire;
    private String goods_tag;
    private String notify_url;
    private String trade_type;
    private String product_id;
    private String limit_pay;
    private String openid;
    private String sub_openid;
}
