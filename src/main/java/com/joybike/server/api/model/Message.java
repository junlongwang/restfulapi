package com.joybike.server.api.model;

import java.io.Serializable;

/**
 * 统一返回结果
 * Created by 58 on 2016/10/16.
 */
public class Message<T> implements Serializable {

    /**
     * 请求是否成功
     */
    private boolean isSucess;
    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 返回数据
     */
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
