package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lishaoyong on 16/10/26.
 */
public class product implements Serializable {

    Long id;


    /**
     * 产品CODe
     */
    String productCode;

    /**
     * 产品名称
     */
    String productName;

    /**
     * 售卖价
     */
    BigDecimal price;

    /**
     * 刊例价
     */
    BigDecimal publishedPrice;

    /**
     * 创建人
     */
    Long createId;

    /**
     * 创建时间
     */
    Integer createAt;

    /**
     * 修改人
     */
    Long updateId;

    /**
     * 创建时间
     */
    Integer updateAt;

    public product(Long id, String productCode, String productName, BigDecimal price, BigDecimal publishedPrice, Long createId, Integer createAt, Long updateId, Integer updateAt) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.price = price;
        this.publishedPrice = publishedPrice;
        this.createId = createId;
        this.createAt = createAt;
        this.updateId = updateId;
        this.updateAt = updateAt;
    }

    public product(){

    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", publishedPrice=" + publishedPrice +
                ", createId=" + createId +
                ", createAt=" + createAt +
                ", updateId=" + updateId +
                ", updateAt=" + updateAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPublishedPrice() {
        return publishedPrice;
    }

    public void setPublishedPrice(BigDecimal publishedPrice) {
        this.publishedPrice = publishedPrice;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Integer getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Integer createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Integer getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Integer updateAt) {
        this.updateAt = updateAt;
    }

}
