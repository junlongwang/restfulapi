package com.joybike.server.api.Enum;

/**
 * Created by lishaoyong on 16/10/24.
 */
public enum ReturnEnum {

    /**
     * 异常定义
     * 1:系统错误
     * 2:支付错误
     * 3:订单错误
     * 4:用户错误
     * 5:车辆
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

    /***********支付错误*******************/
    NoPay_Error(2001,"有未支付的订单"),

    ConsumedOrderList_Error(2006,"获取消费明细失败"),

    BankDepositOrderList_Error(2007,"获取充值明细失败"),

    /***********车辆错误*******************/

    Appointment_Success(5001,"预约成功"),
    Cancel_Success(5002,"取消预约成功"),
    Cancel_Error(5003,"取消预约失败"),
    Repeat_Error(5004,"重复预约"),

    BicycleUse_Error(5005,"车辆已被预约"),

    No_Subscribe(5006,"没有可取消的预约"),

    No_Vehicle(5007,"当前范围内没有车辆使用"),
    Use_Vehicle(5008,"车辆使用中"),
    Unlock_Success(5009,"开锁成功"),
    Disable_Vehicle(5010,"车辆为禁用状态"),
    FaultIng(5011,"车辆不可使用"),
    Unlock_Error(5012,"开锁成功"),
    Submit_Error(5013,"故障申报失败"),








    ;

    @Override
    public String toString() {
        return errorCode + ":" + errorDesc;
    }

    /*===================================================================================*/

    private int errorCode;
    private String errorDesc;

    private ReturnEnum(int errorCode, String errorDesc) {
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
