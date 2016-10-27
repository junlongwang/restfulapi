package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.BankRefundOrderDao;
import com.joybike.server.api.model.bankRefundOrder;
import com.joybike.server.api.util.RestfulException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lishaoyong on 16/10/19.
 */
@Repository("BankRefundOrderDao")
public class BankRefundOrderDaoImpl extends Reository<bankRefundOrder> implements BankRefundOrderDao {

    final String updateRefundOrderStatusById = "update bankRefundOrder set status = 1  where id = :id";


    /**
     * 退款完毕并更新退款订单为退款成功状态
     * @param id
     * @return
     */
    @Override
    public int updateRefundOrderStatusById(Long id){
        try {
            Map map = new HashMap();
            map.put("id", id);
            return execSQL(updateRefundOrderStatusById, map);
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }

}
