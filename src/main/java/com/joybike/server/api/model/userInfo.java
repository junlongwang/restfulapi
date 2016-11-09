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


    public userInfo(){

    }

    public userInfo(Long id, String iphone, String realName, String idNumber, String nationality, String photo, String identityCardphoto, Integer createAt, Integer updateAt, String userImg, Integer securityStatus, Integer authenStatus) {
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
    }

    @Override
    public String toString() {
        return "userInfo{" +
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
                '}';
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
}