package com.joybike.server.api.model;

import java.io.Serializable;

/**
 * Created by 58 on 2016/11/15.
 */
public class sms implements Serializable{

    private long id;
    private String title;
    private String content;
    private int createAt;
    private long creator;
    private String imagUrl;
    private String detailURL;
    private String target;

    public sms(){}

    public sms(String title, String content, int createAt, long creator, String imagUrl, String detailURL, String target) {
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.creator = creator;
        this.imagUrl = imagUrl;
        this.detailURL = detailURL;
        this.target = target;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public String getDetailURL() {
        return detailURL;
    }

    public void setDetailURL(String detailURL) {
        this.detailURL = detailURL;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
