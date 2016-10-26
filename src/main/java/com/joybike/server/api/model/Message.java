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
    private boolean isSucess;
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

    public Message(boolean isSucess, int errorCode, String errorMessage, T data) {
        this.isSucess = isSucess;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public boolean isSucess() {
        return isSucess;
    }

    public void setIsSucess(boolean isSucess) {
        this.isSucess = isSucess;
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
                "isSucess=" + isSucess +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
