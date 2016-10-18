package com.joybike.server.api;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 测试DEMO 实体
 * Created by liyd on 2015-8-6.
 */
@XmlRootElement
public class Message {

    private String name;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
