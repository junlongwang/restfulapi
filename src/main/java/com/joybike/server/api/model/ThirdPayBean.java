package com.joybike.server.api.model;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by LongZiyuan on 2016/10/20.
 */
public class ThirdPayBean {
    private Long   id;         //支付系统订单号

    private BigDecimal orderMoney; //订单金额(元)

    private String orderDesc;  //订单描述

    private String pruductDesc; //商品描述

    private String operIP;     //请求ip

    private String notifyUrl;  //异步通知地址

    private String openid;     //openid

    private Date   createTime; //创建时间

    private Date   recordTime; //到期时间

    private int    channelId;  //渠道id

    private Integer rechargeType;  //充值类型 0余额 1押金

    public ThirdPayBean() {

    }

    public ThirdPayBean(Long id, BigDecimal orderMoney, String orderDesc, String pruductDesc, String operIP, String notifyUrl, String openid, Date createTime, Date recordTime, int channelId, Integer rechargeType) {
        this.id = id;
        this.orderMoney = orderMoney;
        this.orderDesc = orderDesc;
        this.pruductDesc = pruductDesc;
        this.operIP = operIP;
        this.notifyUrl = notifyUrl;
        this.openid = openid;
        this.createTime = createTime;
        this.recordTime = recordTime;
        this.channelId = channelId;
        this.rechargeType = rechargeType;
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

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
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
}
