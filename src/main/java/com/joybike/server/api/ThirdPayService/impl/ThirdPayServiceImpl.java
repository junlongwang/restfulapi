package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.ThirdPayService.appConstructUrlInter;
import com.joybike.server.api.ThirdPayService.IThirdPayService;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;
import org.springframework.stereotype.Service;
import java.util.HashMap;

/**
 * Created by LongZiyuan on 2016/10/20.
 */
@Service
public class ThirdPayServiceImpl implements IThirdPayService {

    @Override
    public String execute(ThirdPayBean payOrder) {
        //payorderUtil
        if(payOrder.getChannelId() == 117)
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("out_trade_no", payOrder.getId().toString());
            map.put("total_fee", payOrder.getOrderMoney().toString());
            map.put("notify_url", payOrder.getNotifyUrl());
            map.put("spbill_create_ip",payOrder.getOperIP());
            map.put("body",payOrder.getOrderDesc());
            RedirectParam redirectParam= new WxAppConstructUrlImpl().getUrl(map);
            if( redirectParam != null )
                return redirectParam.getPara();
        }
        else if(payOrder.getChannelId() == 118){
            RedirectParam redirectParam = new WxPublicConstructUrlImpl().getUrl(payOrder);
            if (redirectParam != null)
                return redirectParam.getPara();
        }
        return "";
    }


    @Override
    public String queryPayResult(ThirdPayBean payOrder) {
        String queryResult = "";
        if (payOrder != null) {
            appConstructUrlInter cui = new WxAppConstructUrlImpl();
            //queryResult = cui.getPayQueryResult(payOrder);
        }
        return queryResult;
    }
}
