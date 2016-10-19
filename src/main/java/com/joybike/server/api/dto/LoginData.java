package com.joybike.server.api.dto;
import com.joybike.server.api.model.*;
/**
 * Created by LongZiyuan on 2016/10/19.
 * 用户短信登录验证实体
 * 1.短信验证码
 * 2.用户信息
 */
public class LoginData {
    /**
     * 短信验证码
     */
    private String validateCode;
    /**
     * 用户信息
     */
    private userInfo userInfo;

    public LoginData()
    {

    }

    public LoginData(String validateCode,userInfo userInfo) {
        this.validateCode = validateCode;
        this.userInfo = userInfo;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public com.joybike.server.api.model.userInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(com.joybike.server.api.model.userInfo userInfo) {
        this.userInfo = userInfo;
    }
}
