package com.joybike.server.api.util;

import com.joybike.server.api.Enum.ReturnEnum;
import org.springframework.core.NestedExceptionUtils;

/**
 * Created by lishaoyong on 16/10/23.
 * 自定义异常
 */
public class RestfulException extends RuntimeException {
    private static final long serialVersionUID = 8156464015933076496L;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 应用错误码枚举
     * @param returnEnum
     */
    public RestfulException(ReturnEnum returnEnum){
        super(returnEnum.getErrorDesc());
        this.errorCode = returnEnum.getErrorCode();
    }

    /**
     * 应用错误码枚举、异常对象(用于实现异常链)
     * @param returnEnum
     * @param cause
     */
    public RestfulException(ReturnEnum returnEnum, Throwable cause){
        super(returnEnum.getErrorDesc(), cause);
        this.errorCode = returnEnum.getErrorCode();
    }

    /**
     * 应用错误码枚举、自定义报错信息
     * 在枚举定时错误消息满足不了时使用，例如：动态的生成报错提示信息
     * @param returnEnum
     * @param message
     */
    public RestfulException(ReturnEnum returnEnum, String message){
        super(message);
        this.errorCode = returnEnum.getErrorCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return NestedExceptionUtils.buildMessage(super.getMessage(), getCause());
    }

}
