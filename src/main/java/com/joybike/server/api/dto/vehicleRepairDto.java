package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 车辆报修接口入参
 * Created by 58 on 2016/10/25.
 */
public class vehicleRepairDto implements Serializable{

    /**
     * 车辆编码
     *  @Author lisy
     **/
    @JSONField(ordinal = 1)
    private String bicycleCode;

    /**
     * 报修原因
     *  @Author lisy
     **/
    @JSONField(ordinal = 2)
    private String cause;

    /**
     * 故障图片
     *  @Author lisy
     **/
    @JSONField(ordinal = 3)
    private byte[] faultImg;

    /**
     * 信息提交人
     *  @Author lisy
     **/
    @JSONField(ordinal = 4)
    private Long createId;

    /**
     * 创建时间
     *  @Author lisy
     **/
    @JSONField(ordinal = 5)
    private Integer createAt;



    public String getBicycleCode() {
        return bicycleCode;
    }

    public void setBicycleCode(String bicycleCode) {
        this.bicycleCode = bicycleCode;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public byte[] getFaultImg() {
        return faultImg;
    }

    public void setFaultImg(byte[] faultImg) {
        this.faultImg = faultImg;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public vehicleRepairDto(String bicycleCode, String cause, byte[] faultImg, Long createId, Integer createAt) {
        this.bicycleCode = bicycleCode;
        this.cause = cause;
        this.faultImg = faultImg;
        this.createId = createId;
        this.createAt = createAt;
    }

    public vehicleRepairDto(){

    }

    @Override
    public String toString() {
        return "vehicleRepairDto{" +
                "bicycleCode='" + bicycleCode + '\'' +
                ", cause='" + cause + '\'' +
                ", faultImg=" + Arrays.toString(faultImg) +
                ", createId=" + createId +
                ", createAt=" + createAt +
                '}';
    }
}
