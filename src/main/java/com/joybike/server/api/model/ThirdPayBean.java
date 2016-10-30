package com.joybike.server.api.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by LongZiyuan on 2016/10/20.
 */
public class ThirdPayBean implements Serializable {
    private Long   id;         //支付系统订单号

    private Long   refundid;    //支付系统退款订单号

    private BigDecimal orderMoney; //订单金额(元)

    private BigDecimal orderMoneyFree; //充值赠送优惠金额（元）

    private String orderDesc;  //订单描述

    private String pruductDesc; //商品描述

    private String operIP;     //请求ip

    private String openid;     //openid

    private Date   createTime; //创建时间

    private Date   recordTime; //到期时间

    private int    channelId;  //渠道id

    private Integer rechargeType;  //充值类型 0余额 1押金

    private String transaction_id;  //微信支付订单号

    private Long cosumeid;  //充值消费关联订单号

    private Long userId;//用户ID

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderMoneyFree() {
        return orderMoneyFree;
    }

    public void setOrderMoneyFree(BigDecimal orderMoneyFree) {
        this.orderMoneyFree = orderMoneyFree;
    }

    public Long getCosumeid() {
        return cosumeid;
    }

    public void setCosumeid(Long cosumeid) {
        this.cosumeid = cosumeid;
    }

    public ThirdPayBean(Long id, Long refundid, BigDecimal orderMoney, BigDecimal orderMoneyFree, String orderDesc, String pruductDesc, String operIP, String openid, Date createTime, Date recordTime, int channelId, Integer rechargeType, String transaction_id,Long cosumeid) {
        this.id = id;
        this.refundid = refundid;
        this.orderMoney = orderMoney;
        this.orderMoneyFree = orderMoneyFree;
        this.orderDesc = orderDesc;
        this.pruductDesc = pruductDesc;
        this.operIP = operIP;
        this.openid = openid;
        this.createTime = createTime;
        this.recordTime = recordTime;
        this.channelId = channelId;
        this.rechargeType = rechargeType;
        this.transaction_id = transaction_id;
        this.cosumeid = cosumeid;

    }
    public ThirdPayBean() {

    }


    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Long getRefundid() {
        return refundid;
    }

    public void setRefundid(Long refundid) {
        this.refundid = refundid;
    }



    public Integer getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(Integer rechargeType) {
        this.rechargeType = rechargeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getPruductDesc() {
        return pruductDesc;
    }

    public void setPruductDesc(String pruductDesc) {
        this.pruductDesc = pruductDesc;
    }

    public String getOperIP() {
        return operIP;
    }

    public void setOperIP(String operIP) {
        this.operIP = operIP;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "ThirdPayBean{" +
                "id=" + id +
                ", refundid=" + refundid +
                ", orderMoney=" + orderMoney +
                ", orderMoneyFree=" + orderMoneyFree +
                ", orderDesc='" + orderDesc + '\'' +
                ", pruductDesc='" + pruductDesc + '\'' +
                ", operIP='" + operIP + '\'' +
                ", openid='" + openid + '\'' +
                ", createTime=" + createTime +
                ", recordTime=" + recordTime +
                ", channelId=" + channelId +
                ", rechargeType=" + rechargeType +
                ", transaction_id='" + transaction_id + '\'' +
                ", cosumeid=" + cosumeid +
                ", userId=" + userId +
                '}';
    }
}
