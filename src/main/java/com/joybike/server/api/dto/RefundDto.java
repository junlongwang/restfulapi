package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by 58 on 2016/10/31.
 */
public class RefundDto {
    @JSONField(ordinal = 1)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RefundDto() {

    }

    public RefundDto(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RefundDto{" +
                "userId=" + userId +
                '}';
    }
}
