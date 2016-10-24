package com.joybike.server.api.model;

/**
 * Created by LongZiyuan on 2016/10/24.
 */
public class WxPay {
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
    private String spbill_create_ip;    //终端IP
    private String time_start;  //交易起始时间
    private String time_expire; //交易结束时间
    private String goods_tag;   //商品标记
    private String notify_url;  //通知地址
    private String trade_type;  //交易类型
    private String product_id;  //商品ID
    private String limit_pay;   //指定支付方式
    private String openid;  //用户标识
    private String sub_openid;  //用户子标识

    public WxPay()
    {

    }

    public WxPay(String appid, String mch_id, String sub_appid, String sub_mch_id, String device_info, String nonce_str, String sign, String body, String detail, String attach, String out_trade_no, int fee_type, String total_fee, String spbill_create_ip, String time_start, String time_expire, String goods_tag, String notify_url, String trade_type, String product_id, String limit_pay, String openid, String sub_openid) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.sub_appid = sub_appid;
        this.sub_mch_id = sub_mch_id;
        this.device_info = device_info;
        this.nonce_str = nonce_str;
        this.sign = sign;
        this.body = body;
        this.detail = detail;
        this.attach = attach;
        this.out_trade_no = out_trade_no;
        this.fee_type = fee_type;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.time_start = time_start;
        this.time_expire = time_expire;
        this.goods_tag = goods_tag;
        this.notify_url = notify_url;
        this.trade_type = trade_type;
        this.product_id = product_id;
        this.limit_pay = limit_pay;
        this.openid = openid;
        this.sub_openid = sub_openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSub_appid() {
        return sub_appid;
    }

    public void setSub_appid(String sub_appid) {
        this.sub_appid = sub_appid;
    }

    public String getSub_mch_id() {
        return sub_mch_id;
    }

    public void setSub_mch_id(String sub_mch_id) {
        this.sub_mch_id = sub_mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getFee_type() {
        return fee_type;
    }

    public void setFee_type(int fee_type) {
        this.fee_type = fee_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSub_openid() {
        return sub_openid;
    }

    public void setSub_openid(String sub_openid) {
        this.sub_openid = sub_openid;
    }
}

