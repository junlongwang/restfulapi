package com.joybike.server.api.model;

import java.io.Serializable;

public class vehicleRepair implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 车辆ID
     *  @Author lisy
    **/
    private Long vehicleId;

    /** 
     * 保修原因
     *  @Author lisy
    **/
    private String cause;

    /** 
     * 故障图片
     *  @Author lisy
    **/
    private String faultImg;

    /** 
     * 信息提交人
     *  @Author lisy
    **/
    private Long createId;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    /** 
     * 处理状态
     *  @Author lisy
    **/
    private Integer disposeStatus;

    /** 
     * 处理描述
     *  @Author lisy
    **/
    private String disposeDepict;

    /** 
     * 处理人
     *  @Author lisy
    **/
    private Long operateId;

    /** 
     * 处理时间
     *  @Author lisy
    **/
    private Integer operateAt;

    public vehicleRepair(){

    }

    public vehicleRepair(Long vehicleId, String cause, String faultImg, Long createId, Integer createAt, Integer disposeStatus, String disposeDepict, Long operateId, Integer operateAt) {
        this.vehicleId = vehicleId;
        this.cause = cause;
        this.faultImg = faultImg;
        this.createId = createId;
        this.createAt = createAt;
        this.disposeStatus = disposeStatus;
        this.disposeDepict = disposeDepict;
        this.operateId = operateId;
        this.operateAt = operateAt;
    }

    @Override
    public String toString() {
        return "vehicleRepair{" +
                "id=" + id +
                ", vehicleId=" + vehicleId +
                ", cause='" + cause + '\'' +
                ", faultImg='" + faultImg + '\'' +
                ", createId=" + createId +
                ", createAt=" + createAt +
                ", disposeStatus=" + disposeStatus +
                ", disposeDepict='" + disposeDepict + '\'' +
                ", operateId=" + operateId +
                ", operateAt=" + operateAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getFaultImg() {
        return faultImg;
    }

    public void setFaultImg(String faultImg) {
        this.faultImg = faultImg == null ? null : faultImg.trim();
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

    public Integer getDisposeStatus() {
        return disposeStatus;
    }

    public void setDisposeStatus(Integer disposeStatus) {
        this.disposeStatus = disposeStatus;
    }

    public String getDisposeDepict() {
        return disposeDepict;
    }

    public void setDisposeDepict(String disposeDepict) {
        this.disposeDepict = disposeDepict == null ? null : disposeDepict.trim();
    }

    public Long getOperateId() {
        return operateId;
    }

    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }

    public Integer getOperateAt() {
        return operateAt;
    }

    public void setOperateAt(Integer operateAt) {
        this.operateAt = operateAt;
    }
}