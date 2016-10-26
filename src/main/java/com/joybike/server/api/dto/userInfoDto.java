package com.joybike.server.api.dto;

import java.io.Serializable;

/**
 * 用户信息更新入参
 * Created by 58 on 2016/10/26.
 */
public class userInfoDto implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

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
    private byte[] photo;

    /**
     * 身份证照片
     *  @Author lisy
     **/
    private byte[] identityCardphoto;

    /**
     * 用户头像图片
     *  @Author lisy
     **/
    private byte[] userImg;

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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getIdentityCardphoto() {
        return identityCardphoto;
    }

    public void setIdentityCardphoto(byte[] identityCardphoto) {
        this.identityCardphoto = identityCardphoto;
    }

    public byte[] getUserImg() {
        return userImg;
    }

    public void setUserImg(byte[] userImg) {
        this.userImg = userImg;
    }
}
