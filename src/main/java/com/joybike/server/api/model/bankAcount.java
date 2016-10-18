package com.joybike.server.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class bankAcount implements Serializable {
    /** 
     * 
     * @Author  lisy
    **/
    private Long id;

    /** 
     * 用户ID
     * @Author  lisy
    **/
    private Long userId;

    /** 
     * 账户类型0:余额,1：优惠
     * @Author  lisy
    **/
    private Integer acountType;

    /** 
     * 0:主站,后期扩展多个业务
     * @Author  lisy
    **/
    private Integer serviceType;

    /** 
     * 可用余额
     * @Author  lisy
    **/
    private BigDecimal price;

    /** 
     * 创建时间
     * @Author  lisy
    **/
    private Integer createAt;

    /** 
     * 更新时间
     * @Author  lisy
    **/
    private Integer updateAt;

    public bankAcount(Long userId, Integer acountType, Integer serviceType, BigDecimal price, Integer createAt, Integer updateAt) {
        this.userId = userId;
        this.acountType = acountType;
        this.serviceType = serviceType;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAcountType() {
        return acountType;
    }

    public void setAcountType(Integer acountType) {
        this.acountType = acountType;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
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

    public Integer getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Integer updateAt) {
        this.updateAt = updateAt;
    }
}