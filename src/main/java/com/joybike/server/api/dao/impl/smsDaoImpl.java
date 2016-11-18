package com.joybike.server.api.dao.impl;

import com.joybike.server.api.Enum.ReturnEnum;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.dao.SubscribeInfoDao;
import com.joybike.server.api.dao.smsDao;
import com.joybike.server.api.model.product;
import com.joybike.server.api.model.sms;
import com.joybike.server.api.model.subscribeInfo;
import com.joybike.server.api.util.RestfulException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 58 on 2016/11/15.
 */
@Repository("smsDao")
public class smsDaoImpl extends Reository<sms> implements smsDao {

    public List<sms> getSmsMessages(long userId)
    {
        try {
            Map map = new HashMap();
            map.put("target", String.valueOf(userId));
            try {
                return this.jdbcTemplate.query("SELECT * FROM `sms` WHERE target=:target || target='all' order by createAt DESC ", map, new BeanPropertyRowMapper(sms.class));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            throw new RestfulException(ReturnEnum.DATABASE_ERROR);
        }
    }
}
