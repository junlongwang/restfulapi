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
     * 全局未知错误
     */
    UNKNOWN(1000,"未知错误"),

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
    SERVICE_ERROR(1003, "服务不可达"),

    /***********
     * 支付错误
     *******************/
    NoPay_Error(2001, "有未支付的订单"),
    Acount_Error(2002, "获取账户余额失败"),
    Recharge_Error(2003,"充值失败"),
    Pay_Low(2004,"余额不足,请充值"),
    ConsumedOrderList_Error(2006, "获取消费明细失败"),

    BankDepositOrderList_Error(2007, "获取充值明细失败"),


    refund_Error(2008, "退款失败"),
    /**
     * 订单 3
     */
    Product_Error(3001,"获取产品列表失败"),
    Order_Eroor(3002,"订单信息错误"),


    /***********
     * 用户
     *******************/
    UpdateUer_ERROR(4001, "更新用户信息失败"),
    UseRregister_Error(4002, "登录失败"),
    Iphone_Error(4003,"验证码发送失败,发送通道异常！"),
    Iphone_Validate_Error(4004, "验证码验证失败！，请重新获取！"),
    Iphone_Sender_Error(4005,"验证码已经发送，5分钟内不允许重复请求！"),
    UerInfo_ERROR(4006,"获取用户信息错误"),
    /***********
     * 车辆错误
     *******************/


    Appointment_Success(5001, "预约成功"),

    Cancel_Success(5002, "取消预约成功"),
    Cancel_Error(5003, "取消预约失败"),
    Repeat_Error(5004, "重复预约"),

    BicycleUse_Error(5005, "车辆已被预约"),

    No_Subscribe(5006, "没有可取消的预约"),

    No_Vehicle(5007, "当前范围内没有车辆使用"),
    Use_Vehicle(5008, "车辆被他人使用中,不可以扫码开锁"),
    Unlock_Success(5009, "开锁成功"),
    Disable_Vehicle(5010, "车辆为禁用状态"),
    FaultIng(5011, "车辆故障,不可使用"),
    Unlock_Error(5012, "开锁失败"),
    Submit_Error(5013, "故障申报失败"),
    Appointment_Error(5014, "预约失败"),
    Use_Self_Vehicle(5015, "您正在使用车辆中"),




    ;

    @Override
    public String toString() {
        return String.valueOf(errorCode);
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
