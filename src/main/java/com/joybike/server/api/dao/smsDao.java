package com.joybike.server.api.dao;

import com.joybike.server.api.Infrustructure.IRepository;
import com.joybike.server.api.Infrustructure.Reository;
import com.joybike.server.api.model.product;
import com.joybike.server.api.model.sms;
import com.joybike.server.api.model.vehicle;

import java.util.List;

/**
 * Created by 58 on 2016/11/15.
 */

public interface smsDao extends   IRepository<sms> {
    List<sms> getSmsMessages(long userId);
 }
