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
     * 程序具体业务逻辑相关
     */
    SERVICE_ERROR(1003, "service error"),


    // 参数相关{从1020010001~1020010099}
    PARAMETER_ERROR(1004, "参数不符合要求"),

    // 接口执行相关{从1020010100开始}
    ORDER_ERROR(1005, "订单操作失败"),
    Bicycle_Error(1006,"车辆操作失败"),
    Pay_Error(1007,"支付失败"),
    User_Error(1008,"用户信息错误"),

    Repeat_Error(1009,"重复预约"),

    BicycleUse_Error(1010,"车辆使用中"),

    ConsumedOrderList_Error(1011,"获取消费明细失败"),

    BankDepositOrderList_Error(1012,"获取充值明细失败"),



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
