package com.joybike.server.api.util;

/**
 * Created by lishaoyong on 16/10/23.
 * 自定义异常
 */
public class RestfulException extends RuntimeException {

    public RestfulException(String errorCode){
        super(errorCode);
    }

    public RestfulException(String errorCode, Throwable e){
        super(errorCode, e);
    }
}
