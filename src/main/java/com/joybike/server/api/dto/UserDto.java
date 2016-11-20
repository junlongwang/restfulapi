package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/11/15.
 */
public class UserDto implements Serializable {
    /**
     *
     *  @Author lisy
     **/
    @JSONField(ordinal = 1)
    private Long id;

    /**
     * 电话
     */
    @JSONField(ordinal = 2)
    private String iphone;

    /**
     * 用户姓名
     *  @Author lisy
     **/
    @JSONField(ordinal = 3)
    private String realName;

    /**
     * 身份证编号
     *  @Author lisy
     **/
    @JSONField(ordinal = 4)
    private String idNumber;

    /**
     * 国籍
     *  @Author lisy
     **/
    @JSONField(ordinal = 5)
    private String nationality;

    /**

     * 身份证和人合照，图
     *  @Author lisy
     **/
    @JSONField(ordinal = 6)
    private String photo;

    /**
     *
     * 身份证
     *  @Author lisy
     **/
    @JSONField(ordinal = 7)
    private String identityCardphoto;

    /**
     * 创建
     *  @Author lisy
     **/
    @JSONField(ordinal = 8)
    private Integer createAt;

    /**
     *
     *  @Author lisy
     **/
    @JSONField(ordinal = 9)
    private Integer updateAt;

    /**
     * 用户头像图
     *  @Author lisy
     **/
    @JSONField(ordinal = 10)
    private String userImg;

    /**
     * 押金状态:0,无或已退款;1:正常，2：退款中
     *  @Author lisy
     **/
    @JSONField(ordinal = 11)
    private Integer securityStatus;

    /**
     * 认证状态0：未认证 ，1：已认证
     *  @Author lisy
     **/
    @JSONField(ordinal = 12)
    private Integer authenStatus;


    /**
     * 总里程
     */
    @JSONField(ordinal = 13)
    private BigDecimal totalMileage;


    /**
     * 总余额
     */
    @JSONField(ordinal = 14)
    private BigDecimal amount;


    /**
     * 总时间
     */
    @JSONField(ordinal = 15)
    private Integer totalcyclingTime;

    /**
     * 目标设备ID或者OPENID
     */
    @JSONField(ordinal = 16)
    private String guid;

    /**
     * 微信openid
     */
    private String openId;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", iphone='" + iphone + '\'' +
                ", realName='" + realName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", photo='" + photo + '\'' +
                ", identityCardphoto='" + identityCardphoto + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", userImg='" + userImg + '\'' +
                ", securityStatus=" + securityStatus +
                ", authenStatus=" + authenStatus +
                ", totalMileage=" + totalMileage +
                ", amount=" + amount +
                ", totalcyclingTime=" + totalcyclingTime +
                ", guid='" + guid + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }

    public UserDto(){
    }

    public UserDto(Long id, String iphone, String realName, String idNumber, String nationality, String photo, String identityCardphoto, Integer createAt, Integer updateAt, String userImg, Integer securityStatus, Integer authenStatus, BigDecimal totalMileage, BigDecimal amount, Integer totalcyclingTime, String guid, String openId) {
        this.id = id;
        this.iphone = iphone;
        this.realName = realName;
        this.idNumber = idNumber;
        this.nationality = nationality;
        this.photo = photo;
        this.identityCardphoto = identityCardphoto;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.userImg = userImg;
        this.securityStatus = securityStatus;
        this.authenStatus = authenStatus;
        this.totalMileage = totalMileage;
        this.amount = amount;
        this.totalcyclingTime = totalcyclingTime;
        this.guid = guid;
        this.openId = openId;
    }

    public Integer getTotalcyclingTime() {
        return totalcyclingTime;
    }

    public void setTotalcyclingTime(Integer totalcyclingTime) {
        this.totalcyclingTime = totalcyclingTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIdentityCardphoto() {
        return identityCardphoto;
    }

    public void setIdentityCardphoto(String identityCardphoto) {
        this.identityCardphoto = identityCardphoto;
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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(Integer securityStatus) {
        this.securityStatus = securityStatus;
    }

    public Integer getAuthenStatus() {
        return authenStatus;
    }

    public void setAuthenStatus(Integer authenStatus) {
        this.authenStatus = authenStatus;
    }

    public BigDecimal getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(BigDecimal totalMileage) {
        this.totalMileage = totalMileage;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
