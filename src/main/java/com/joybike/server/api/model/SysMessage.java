package com.joybike.server.api.model;

import java.io.Serializable;

/**
 * 系统消息
 * Created by 58 on 2016/10/16.
 */
public class SysMessage implements Serializable {
    private String title;
    private String content;
    private int createAt;
    private int expireAt;

    public SysMessage(String title, String content, int createAt,int expireAt) {
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.expireAt = expireAt;
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

    public int getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(int expireAt) {
        this.expireAt = expireAt;
    }
}
