package com.joybike.server.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP定时上传GPS数据
 * Created by 58 on 2016/10/31.
 */
public class vehicleGpsDataDto implements Serializable {

    /**
     * 车辆印刷的车辆编码
     *  @Author lisy
     **/
    private String bicycleCode;

    /**
     * GPS的纬度
     *  @Author lisy
     **/
    private BigDecimal dimension;

    /**
     * GPS的经度
     *  @Author lisy
     **/
    private BigDecimal longitude;

    /**
     * 创建时间
     *  @Author lisy
     **/
    private Integer createAt;

    public String getBicycleCode() {
        return bicycleCode;
    }

    public void setBicycleCode(String bicycleCode) {
        this.bicycleCode = bicycleCode;
    }

    public BigDecimal getDimension() {
        return dimension;
    }

    public void setDimension(BigDecimal dimension) {
        this.dimension = dimension;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public vehicleGpsDataDto(){}

    public vehicleGpsDataDto(String bicycleCode, BigDecimal dimension, BigDecimal longitude, Integer createAt) {
        this.bicycleCode = bicycleCode;
        this.dimension = dimension;
        this.longitude = longitude;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "vehicleGpsData{" +
                "bicycleCode='" + bicycleCode + '\'' +
                ", dimension=" + dimension +
                ", longitude=" + longitude +
                ", createAt=" + createAt +
                '}';
    }
}
