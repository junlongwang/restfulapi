package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class bankDepositOrder implements Serializable {
    /** 
     * 
     * @Author lisy
    **/
    private Long id;

    /** 
     * 用户ID
     * @Author lisy
    **/
    private Long userId;

    /** 
     * 现金
     * @Author lisy
    **/
    private BigDecimal cash;

    /** 
     * 优惠
     * @Author lisy
    **/
    private BigDecimal award;

    /** 
     * 赠送到期时间
     * @Author lisy
    **/
    private Integer discountAt;

    /** 
     * 剩余现金
     * @Author lisy
    **/
    private BigDecimal residualCash;

    /** 
     * 剩余优惠
     * @Author lisy
    **/
    private BigDecimal residualAward;

    /** 
     * 充值状态（1：初始，2：成功）
     * @Author lisy
    **/
    private Integer status;

    /** 
     * 支付方式：0支付宝，1：微信
     * @Author lisy
    **/
    private Integer payType;

    /** 
     * 支付账户ID
     * @Author lisy
    **/
    private String payAcount;

    /** 
     * 交易凭证号
     * @Author lisy
    **/
    private String payDocumentid;

    /** 
     * 商户ID
     * @Author lisy
    **/
    private String merchantId;

    /** 
     * 备注
     * @Author lisy
    **/
    private String remark;

    /** 
     * 交易时间
     * @Author lisy
    **/
    private Integer payAt;

    /** 
     * 创建时间
     * @Author lisy
    **/
    private Integer createAt;

    public bankDepositOrder(){

    }

    public bankDepositOrder(Long userId, BigDecimal cash, BigDecimal award, Integer discountAt, BigDecimal residualCash, BigDecimal residualAward, Integer status, Integer payType, String payAcount, String payDocumentid, String merchantId, String remark, Integer payAt, Integer createAt) {
        this.userId = userId;
        this.cash = cash;
        this.award = award;
        this.discountAt = discountAt;
        this.residualCash = residualCash;
        this.residualAward = residualAward;
        this.status = status;
        this.payType = payType;
        this.payAcount = payAcount;
        this.payDocumentid = payDocumentid;
        this.merchantId = merchantId;
        this.remark = remark;
        this.payAt = payAt;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "bankDepositOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", cash=" + cash +
                ", award=" + award +
                ", discountAt=" + discountAt +
                ", residualCash=" + residualCash +
                ", residualAward=" + residualAward +
                ", status=" + status +
                ", payType=" + payType +
                ", payAcount='" + payAcount + '\'' +
                ", payDocumentid='" + payDocumentid + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", remark='" + remark + '\'' +
                ", payAt=" + payAt +
                ", createAt=" + createAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getAward() {
        return award;
    }

    public void setAward(BigDecimal award) {
        this.award = award;
    }

    public Integer getDiscountAt() {
        return discountAt;
    }

    public void setDiscountAt(Integer discountAt) {
        this.discountAt = discountAt;
    }

    public BigDecimal getResidualCash() {
        return residualCash;
    }

    public void setResidualCash(BigDecimal residualCash) {
        this.residualCash = residualCash;
    }

    public BigDecimal getResidualAward() {
        return residualAward;
    }

    public void setResidualAward(BigDecimal residualAward) {
        this.residualAward = residualAward;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayAcount() {
        return payAcount;
    }

    public void setPayAcount(String payAcount) {
        this.payAcount = payAcount == null ? null : payAcount.trim();
    }

    public String getPayDocumentid() {
        return payDocumentid;
    }

    public void setPayDocumentid(String payDocumentid) {
        this.payDocumentid = payDocumentid == null ? null : payDocumentid.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPayAt() {
        return payAt;
    }

    public void setPayAt(Integer payAt) {
        this.payAt = payAt;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}