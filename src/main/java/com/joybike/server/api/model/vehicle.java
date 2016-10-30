package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class vehicle implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 车辆印刷的车辆ID
     *  @Author lisy
    **/
    private String vehicleId;

    /** 
     * 车锁ID
     *  @Author lisy
    **/
    private Long lockId;

    /** 
     * 绑定电话
     *  @Author lisy
    **/
    private String bundlingPhone;

    /** 
     * 隶属代理商ID
     *  @Author lisy
    **/
    private Integer agentId;

    /** 
     * 状态0车辆启用/1禁用状态/2故障
     *  @Author lisy
    **/
    private Integer status;

    /** 
     * 使用状态0:空闲/1:预约/2:使用
     *  @Author lisy
    **/
    private Integer useStatus;

    /** 
     * 最后一次上报的有效纬度
     *  @Author lisy
    **/
    private BigDecimal lastDimension;

    /** 
     * 最后一次上报的有效经度
     *  @Author lisy
    **/
    private BigDecimal lastLongitude;

    /** 
     * 车辆图片
     *  @Author lisy
    **/
    private String vehicleImg;

    /** 
     * 备注
     *  @Author lisy
    **/
    private String remark;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    /** 
     * 更新时间
     *  @Author lisy
    **/
    private Integer updateAt;

    public vehicle(){

    }

    public vehicle(Long id, String vehicleId, Long lockId, String bundlingPhone, Integer agentId, Integer status, Integer useStatus, BigDecimal lastDimension, BigDecimal lastLongitude, String vehicleImg, String remark, Integer createAt, Integer updateAt) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.lockId = lockId;
        this.bundlingPhone = bundlingPhone;
        this.agentId = agentId;
        this.status = status;
        this.useStatus = useStatus;
        this.lastDimension = lastDimension;
        this.lastLongitude = lastLongitude;
        this.vehicleImg = vehicleImg;
        this.remark = remark;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "vehicle{" +
                "id=" + id +
                ", vehicleId='" + vehicleId + '\'' +
                ", lockId=" + lockId +
                ", bundlingPhone='" + bundlingPhone + '\'' +
                ", agentId=" + agentId +
                ", status=" + status +
                ", useStatus=" + useStatus +
                ", lastDimension=" + lastDimension +
                ", lastLongitude=" + lastLongitude +
                ", vehicleImg='" + vehicleImg + '\'' +
                ", remark='" + remark + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId == null ? null : vehicleId.trim();
    }

    public void setLockId(Long lockId) {
        this.lockId = lockId;
    }

    public Long getLockId() {
        return lockId;
    }

    public String getBundlingPhone() {
        return bundlingPhone;
    }

    public void setBundlingPhone(String bundlingPhone) {
        this.bundlingPhone = bundlingPhone == null ? null : bundlingPhone.trim();
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public BigDecimal getLastDimension() {
        return lastDimension;
    }

    public void setLastDimension(BigDecimal lastDimension) {
        this.lastDimension = lastDimension;
    }

    public BigDecimal getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(BigDecimal lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public String getVehicleImg() {
        return vehicleImg;
    }

    public void setVehicleImg(String vehicleImg) {
        this.vehicleImg = vehicleImg == null ? null : vehicleImg.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public Integer getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Integer updateAt) {
        this.updateAt = updateAt;
    }
}