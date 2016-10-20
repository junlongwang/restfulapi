package com.joybike.server.api.dao;

import com.joybike.server.api.Enum.AcountType;
import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.model.bankAcount;

import java.math.BigDecimal;


/**
 * Created by lishaoyong on 16/10/18.
 */
public interface BankAcountDao extends IRepository<bankAcount> {

    /**
     * 修改用户账户
     * @param userId
     * @param acountType
     * @param price
     * @return
     */
    int updateAcount(long userId,AcountType acountType,BigDecimal price);


    /**
     * 获取用户账户余额
     * @param userId
     * @param acountType
     * @return
     */
    bankAcount getAcount(long userId ,AcountType acountType);

}
