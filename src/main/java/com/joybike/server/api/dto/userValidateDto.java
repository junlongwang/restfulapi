package com.joybike.server.api.dto;

import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * Created by 58 on 2016/11/14.
 */
public class userValidateDto implements Serializable {
    private String mobile;
    private String validateCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public userValidateDto(){}

    public userValidateDto(String mobile, String validateCode) {
        this.mobile = mobile;
        this.validateCode = validateCode;
    }
}
