package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/24.
 */
public enum ErrorEnum {

    /**
     * 异常定义
     * 1001 数据
     */

    /**
     * 数据库操作相关
     */
    DATABASE_ERROR(1001, "database error"),


    /**
     * 调用三方系统相关
     */
    DEPENDENCY_ERROR(1002, "dependencySys error"),

    /**
     * 服务层错误统一
     */
    SERVICE_ERROR(1003, "service error"),


    Repeat_Error(1004,"重复预约"),

    BicycleUse_Error(1005,"车辆使用中"),

    ConsumedOrderList_Error(1006,"获取消费明细失败"),

    BankDepositOrderList_Error(1007,"获取充值明细失败"),



    ;

    @Override
    public String toString() {
        return errorCode + ":" + errorDesc;
    }

    /*===================================================================================*/

    private int errorCode;
    private String errorDesc;

    private ErrorEnum(int errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

}
