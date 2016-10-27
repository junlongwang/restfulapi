package com.joybike.server.api.model;

import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
/**
 * 统一返回结果
 * Created by 58 on 2016/10/16.
 */
public class Message<T> implements Serializable {

    /**
     * 请求是否成功
     */
    @JSONField
    private boolean isSuccess;
    /**
     * 错误码
     */
    @JSONField
    private int errorCode;

    /**
     * 错误信息
     */
    @JSONField
    private String errorMessage;
    /**
     * 返回数据
     */
    @JSONField
    private T data;

    public Message()
    {}

    public Message(boolean isSuccess, int errorCode, String errorMessage, T data) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "isSuccess=" + isSuccess +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
