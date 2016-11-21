package com.joybike.server.api.dto;

/**
 * Created by 58 on 2016/11/21.
 */
public class uploadParkingImgDto {

    private long userId;
    private String bicycleCode;
    private String ParkingImg;
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
