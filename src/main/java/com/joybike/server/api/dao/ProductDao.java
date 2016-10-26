package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.product;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lishaoyong on 16/10/26.
 */
public interface ProductDao extends IRepository<product> {

    /**
     * 修改产品信息
     *
     * @param id
     * @param productName
     * @param price
     * @param publishedPrice
     * @return
     */
    int updateProduct(long id, String productName, BigDecimal price, BigDecimal publishedPrice) throws Exception;


    /**
     * 删除产品
     * @param id
     * @return
     */
    int deleteById(long id) throws Exception;

    /**
     * 获取当前所有产品
     * @return
     */
    List<product> getProductList();
}
