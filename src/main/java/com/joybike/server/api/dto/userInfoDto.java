package com.joybike.server.api.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用户信息更新入参
 * Created by 58 on 2016/10/26.
 */
public class userInfoDto implements Serializable {

    /**
     * 用户ID
     */
    @JSONField(ordinal = 1)
    private Long userId;

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
     * 身份证和人合照，图片
     *  @Author lisy
     **/
    @JSONField(ordinal = 6)
    private String photo;

    /**
     * 身份证照片
     *  @Author lisy
     **/
    @JSONField(ordinal = 7)
    private String identityCardphoto;

    /**
     * 用户头像图片
     *  @Author lisy
     **/
    @JSONField(ordinal = 8)
    private String userImg;

    /**
     * 目标设备ID或者OPENID
     */
    @JSONField(ordinal = 9)
    private String guid;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public userInfoDto(){}


    public userInfoDto(Long userId, String iphone, String realName, String idNumber, String nationality, String photo, String identityCardphoto, String userImg, String guid) {
        this.userId = userId;
        this.iphone = iphone;
        this.realName = realName;
        this.idNumber = idNumber;
        this.nationality = nationality;
        this.photo = photo;
        this.identityCardphoto = identityCardphoto;
        this.userImg = userImg;
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "userInfoDto{" +
                "userId=" + userId +
                ", iphone='" + iphone + '\'' +
                ", realName='" + realName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", photo='" + photo + '\'' +
                ", identityCardphoto='" + identityCardphoto + '\'' +
                ", userImg='" + userImg + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }
}
