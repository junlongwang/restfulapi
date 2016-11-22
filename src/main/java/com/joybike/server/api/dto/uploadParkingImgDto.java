package com.joybike.server.api.dto;

/**
 * Created by 58 on 2016/11/21.
 */
public class uploadParkingImgDto {

    /**
     * 用户ID
     */
    private long userId;
    /**
     * 车辆编码
     */
    private String bicycleCode;
    /**
     * 车辆停放位置图片URL
     */
    private String ParkingImg;
    /**
     * 车辆停放位置备注文字信息
     */
    private String remark;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBicycleCode() {
        return bicycleCode;
    }

    public void setBicycleCode(String bicycleCode) {
        this.bicycleCode = bicycleCode;
    }

    public String getParkingImg() {
        return ParkingImg;
    }

    public void setParkingImg(String parkingImg) {
        ParkingImg = parkingImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public uploadParkingImgDto(){}

    public uploadParkingImgDto(long userId, String bicycleCode, String parkingImg, String remark) {
        this.userId = userId;
        this.bicycleCode = bicycleCode;
        ParkingImg = parkingImg;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "uploadParkingImgDto{" +
                "userId=" + userId +
                ", bicycleCode='" + bicycleCode + '\'' +
                ", ParkingImg='" + ParkingImg + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
