package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class coupon implements Serializable {
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
     * 折扣金额
     */
    private BigDecimal discount;


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
    private BigDecimal discountPrice;

    /** 
     * 创建时间
     *  @Author lisy
    **/
    private Integer createAt;

    public coupon(){

    }

    public coupon(Long id, String name, BigDecimal discount, String descri, Integer expireAt, BigDecimal discountPrice, Integer createAt) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.descri = descri;
        this.expireAt = expireAt;
        this.discountPrice = discountPrice;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                ", descri='" + descri + '\'' +
                ", expireAt=" + expireAt +
                ", discountPrice=" + discountPrice +
                ", createAt=" + createAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
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

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }
}