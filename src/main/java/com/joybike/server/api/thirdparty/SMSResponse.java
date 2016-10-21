package com.joybike.server.api.thirdparty;

/**
 * 发送短信返回结果
 * Created by 58 on 2016/10/21.
 */
public class SMSResponse {

    //{"transactId":"MtrtgVXPJfPR4rvpHlH20161020","errorCode":"0","msg":"正常"}

    private String transactId;
    private  String errorCode;
    private  String msg;

    public SMSResponse(){}

    public SMSResponse(String transactId, String errorCode, String msg) {
        this.transactId = transactId;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public String getTransactId() {
        return transactId;
    }

    public void setTransactId(String transactId) {
        this.transactId = transactId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SMSResponse{" +
                "transactId='" + transactId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
