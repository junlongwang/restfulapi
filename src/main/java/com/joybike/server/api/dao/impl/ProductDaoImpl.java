package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.ProductDao;
import com.joybike.server.api.model.product;
import com.joybike.server.api.util.RestfulException;
import com.joybike.server.api.util.UnixTimeUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/26.
 */
@Repository("productDao")
public class ProductDaoImpl extends Reository<product> implements ProductDao {

    /**
     * 修改产品信息
     *
     * @param id
     * @param productName
     * @param price
     * @param publishedPrice
     * @return
     */
    final String updateProductSql = "update product set productName = :productName,price = :price,publishedPrice = :publishedPrice ,updateAt = :updateAt where id = :id";

    @Override
    public int updateProduct(long id, String productName, BigDecimal price, BigDecimal publishedPrice) throws Exception {
        try {
            Map map = new HashMap();
            map.put("productName", productName);
            map.put("price", price);
            map.put("publishedPrice", publishedPrice);
            map.put("id", id);
            map.put("updateAt", UnixTimeUtils.now());
            return execSQL(updateProductSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 删除产品
     *
     * @param id
     * @return
     */
    final String deleteByIdSql = "delete from product where id = :id";

    @Override
    public int deleteById(long id) throws Exception {
        try {
            Map map = new HashMap();
            map.put("id", id);
            return execSQL(deleteByIdSql, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

    /**
     * 获取当前所有产品
     *
     * @return
     */
    final String getProductSql = "select * from product where productCode = ? ";

    @Override
    public List<product> getProductList() {

        try {

            Object[] object = new Object[]{"pd_recharge"};
            try {
                return this.jdbcTemplate.getJdbcOperations().query(getProductSql, object, new BeanPropertyRowMapper(product.class));
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
