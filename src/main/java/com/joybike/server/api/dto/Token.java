package com.joybike.server.api.dto;

import java.io.Serializable;

/**
 * 接口安全认证Token
 * Created by 58 on 2016/10/27.
 */
public class Token implements Serializable {

    /**
     * 访问接口Token
     */
    private String access_token;

    /**
     * access_token过期时间
     */
    private int expires_in;

    public Token(){}

    public Token(String access_token, int expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "Token{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
