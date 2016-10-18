package com.joybike.server.api.model;

/**
 * 统一返回结果
 * Created by 58 on 2016/10/16.
 */
public class Message<T> {

    private boolean isSucess;
    private String errorCode;
    private T data;

    public Message(boolean isSucess, String errorCode, T data) {
        this.isSucess = isSucess;
        this.errorCode = errorCode;
        this.data = data;
    }

    public boolean isSucess() {
        return isSucess;
    }

    public void setIsSucess(boolean isSucess) {
        this.isSucess = isSucess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
