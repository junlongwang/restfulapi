package com.joybike.server.api.model;

import com.joybike.server.api.Enum.AccountType;
import com.joybike.server.api.Enum.PayType;

import java.math.BigDecimal;

/**
 * 充值订单-充值流水
 * Created by 58 on 2016/10/16.
 */
public class DepositOrder {
    private long id;
    private long userId;
    private BigDecimal amount;
    private BigDecimal awardAmount;
    private int awardExpireTime;
    private BigDecimal leftAmount;
    private BigDecimal leftAwardAmount;
    private PayType payType;
    private String payAccount;
    private AccountType accountType;
    private String transactionCode;
    private int transactionAt;
    private String remark;

    public DepositOrder(long userId, BigDecimal amount, BigDecimal awardAmount, int awardExpireTime, BigDecimal leftAmount, BigDecimal leftAwardAmount, PayType payType, String payAccount, AccountType accountType, String transactionCode, int transactionAt, String remark) {
        this.userId = userId;
        this.amount = amount;
        this.awardAmount = awardAmount;
        this.awardExpireTime = awardExpireTime;
        this.leftAmount = leftAmount;
        this.leftAwardAmount = leftAwardAmount;
        this.payType = payType;
        this.payAccount = payAccount;
        this.accountType = accountType;
        this.transactionCode = transactionCode;
        this.transactionAt = transactionAt;
        this.remark = remark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(BigDecimal awardAmount) {
        this.awardAmount = awardAmount;
    }

    public int getAwardExpireTime() {
        return awardExpireTime;
    }

    public void setAwardExpireTime(int awardExpireTime) {
        this.awardExpireTime = awardExpireTime;
    }

    public BigDecimal getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(BigDecimal leftAmount) {
        this.leftAmount = leftAmount;
    }

    public BigDecimal getLeftAwardAmount() {
        return leftAwardAmount;
    }

    public void setLeftAwardAmount(BigDecimal leftAwardAmount) {
        this.leftAwardAmount = leftAwardAmount;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public int getTransactionAt() {
        return transactionAt;
    }

    public void setTransactionAt(int transactionAt) {
        this.transactionAt = transactionAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
