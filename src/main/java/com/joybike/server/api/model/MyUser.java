package com.joybike.server.api.model;

/**
 * APP注册用户
 * Created by 58 on 2016/10/16.
 */
public class MyUser {
    private long id;
    private String realName;
    private String IDNumber;
    private String nationality;
    private String photo;
    private String identityCardPhoto;
    private String mobilePhone;
    private String wxName;
    private String wxOpenId;
    private String userHeadPhoto;
    private int createAt;
    private int lastUpdateAt;

    public MyUser(){}

    public MyUser(String realName, String IDNumber, String nationality, String photo, String identityCardPhoto, String mobilePhone, String wxName, String wxOpenId, String userHeadPhoto, int createAt, int lastUpdateAt) {
        this.realName = realName;
        this.IDNumber = IDNumber;
        this.nationality = nationality;
        this.photo = photo;
        this.identityCardPhoto = identityCardPhoto;
        this.mobilePhone = mobilePhone;
        this.wxName = wxName;
        this.wxOpenId = wxOpenId;
        this.userHeadPhoto = userHeadPhoto;
        this.createAt = createAt;
        this.lastUpdateAt = lastUpdateAt;
    }

    public String getUserHeadPhoto() {
        return userHeadPhoto;
    }

    public void setUserHeadPhoto(String userHeadPhoto) {
        this.userHeadPhoto = userHeadPhoto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
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

    public String getIdentityCardPhoto() {
        return identityCardPhoto;
    }

    public void setIdentityCardPhoto(String identityCardPhoto) {
        this.identityCardPhoto = identityCardPhoto;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(int lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
}
