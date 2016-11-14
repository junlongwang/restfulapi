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
    private String target;
    private int status;

    public sms(){}

    public sms(long id, String title, String content, int createAt, long creator, String target, int status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.creator = creator;
        this.target = target;
        this.status = status;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
