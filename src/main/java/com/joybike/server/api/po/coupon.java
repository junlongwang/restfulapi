package com.joybike.server.api.po;

import java.math.BigDecimal;

public class coupon {
    /** 
     * 
     *  @Author lisy
    **/
    private Long id;

    /** 
     * 优惠券名称
     *  @Author lisy
    **/
    private String name;

    /** 
     * 描述信息
     *  @Author lisy
    **/
    private String descri;

    /** 
     * 过期时间
     *  @Author lisy
    **/
    private Integer expireAt;

    /** 
     * 剩余优惠
     *  @Author lisy
    **/
    private BigDecimal price;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public coupon(String name, String descri, Integer expireAt, BigDecimal price, Integer createAt) {
        this.name = name;
        this.descri = descri;
        this.expireAt = expireAt;
        this.price = price;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri == null ? null : descri.trim();
    }

    public Integer getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Integer expireAt) {
        this.expireAt = expireAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}