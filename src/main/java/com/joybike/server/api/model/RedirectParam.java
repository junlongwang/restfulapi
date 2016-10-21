package com.joybike.server.api.model;

import java.io.Serializable;

/**
 * Created by LongZiyuan on 2016/10/20.
 */
public class RedirectParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String            method;

    private String            para;

    private String            action;

    private String            character        = "utf-8"; //设置编码格式

    /**
     * Getter method for property <tt>character</tt>.
     *
     * @return property value of character
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Setter method for property <tt>character</tt>.
     *
     * @param character value to be assigned to property character
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
