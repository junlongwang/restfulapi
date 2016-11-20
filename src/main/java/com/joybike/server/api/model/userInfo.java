package com.joybike.server.api.model;

import com.joybike.server.api.thirdparty.aliyun.oss.OSSConsts;

import java.io.Serializable;

public class userInfo implements Serializable {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /**
     * 电话
     */
    private String iphone;

    /**
     * IOS和安卓的设备号，用户消息推送
     */
    private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 用户姓名
     *  @Author lisy
    **/
    private String realName;

    /** 
     * 身份证编号
     *  @Author lisy
    **/
    private String idNumber;

    /** 
     * 国籍
     *  @Author lisy
    **/
    private String nationality;

    /** 

     * 身份证和人合照，图片
     *  @Author lisy
    **/
    private String photo;

    /**
     *
     * 身份证照片
     *  @Author lisy
    **/
    private String identityCardphoto;

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

    /** 
     * 用户头像图片
     *  @Author lisy
    **/
    private String userImg;

    /** 
     * 押金状态:0,无或已退款;1:正常，2：退款中
     *  @Author lisy
    **/
    private Integer securityStatus;

    /** 
     * 认证状态0：未认证 ，1：已认证
     *  @Author lisy
    **/
    private Integer authenStatus;


    /**
     * 微信openid
     */
    private String openId;


    /**
     * IOS和安卓的设备区分，ISO:iso ,安卓:android
     */
    private String targetType;

    public userInfo(){

    }

    public userInfo(Long id, String iphone, String guid, String realName, String idNumber, String nationality, String photo, String identityCardphoto, Integer createAt, Integer updateAt, String userImg, Integer securityStatus, Integer authenStatus, String openId, String targetType) {
        this.id = id;
        this.iphone = iphone;
        this.guid = guid;
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
        this.openId = openId;
        this.targetType = targetType;
    }

    @Override
    public String toString() {
        return "userInfo{" +
                "id=" + id +
                ", iphone='" + iphone + '\'' +
                ", guid='" + guid + '\'' +
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
                ", openId='" + openId + '\'' +
                ", targetType='" + targetType + '\'' +
                '}';
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }


    public String getIdentityCardphoto() {
        return identityCardphoto;
    }

    public void setIdentityCardphoto(String identityCardphoto) {
        this.identityCardphoto = identityCardphoto == null ? null : identityCardphoto.trim();
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
        this.userImg = userImg == null ? null : userImg.trim();
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}