package com.joybike.server.api.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by 58 on 2016/10/31.
 */
@XStreamAlias("xml")
public class WxNotifyOrder {
    //微信appid
    private String appid;

    //微信attach
    private String attach;

    //微信付款一行
    private String bank_type;

    //微信现金支付金额
    private String cash_fee;

    //微信货币类型
    private String fee_type;

    //微信是否关注公众号
    private String is_subscribe;

    //微信商户号
    private String mch_id;

    //微信随机字符串
    private String nonce_str;

    //微信openid
    private String openid;

    //微信外部订单号
    private String out_trade_no;

    //微信结果代码
    private String result_code;

    //微信返回代码
    private String return_code;

    //微信签名
    private String sign;

    //微信支付时间
    private String time_end;

    //微信总金额
    private String total_fee;

    //微信交易类型
    private String trade_type;

    //微信支付订单号
    private String transaction_id;

    public WxNotifyOrder() {

    }

    public WxNotifyOrder(String appid, String attach, String bank_type, String cash_fee, String fee_type, String is_subscribe, String mch_id, String nonce_str, String openid, String out_trade_no, String result_code, String return_code, String sign, String time_end, String total_fee, String trade_type, String transaction_id) {
        this.appid = appid;
        this.attach = attach;
        this.bank_type = bank_type;
        this.cash_fee = cash_fee;
        this.fee_type = fee_type;
        this.is_subscribe = is_subscribe;
        this.mch_id = mch_id;
        this.nonce_str = nonce_str;
        this.openid = openid;
        this.out_trade_no = out_trade_no;
        this.result_code = result_code;
        this.return_code = return_code;
        this.sign = sign;
        this.time_end = time_end;
        this.total_fee = total_fee;
        this.trade_type = trade_type;
        this.transaction_id = transaction_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    @Override
    public String toString() {
        return "WxNotifyOrder{" +
                "appid='" + appid + '\'' +
                ", attach='" + attach + '\'' +
                ", bank_type='" + bank_type + '\'' +
                ", cash_fee='" + cash_fee + '\'' +
                ", fee_type='" + fee_type + '\'' +
                ", is_subscribe='" + is_subscribe + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", openid='" + openid + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", result_code='" + result_code + '\'' +
                ", return_code='" + return_code + '\'' +
                ", sign='" + sign + '\'' +
                ", time_end='" + time_end + '\'' +
                ", total_fee='" + total_fee + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                '}';
    }
}
