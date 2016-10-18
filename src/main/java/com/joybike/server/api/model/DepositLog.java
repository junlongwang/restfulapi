package com.joybike.server.api.model;

import java.math.BigDecimal;

/**
 * 充值明细：押金充值、余额充值、押金退还等
 * Created by 58 on 2016/10/16.
 */
public class DepositLog {
    private int createAt;
    private String title;
    private BigDecimal amount;

    public DepositLog(int createAt, String title, BigDecimal amount) {
        this.createAt = createAt;
        this.title = title;
        this.amount = amount;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
