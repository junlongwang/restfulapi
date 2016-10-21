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
            String appid ="longziyuan";
            map.put("appid",appid);
            map.put("channel_id",String.valueOf(payOrder.getChannelId()));
            RedirectParam redirectPara= new WxAppConstructUrlImpl().getUrl(map);
            if( redirectPara != null )
                return redirectPara.getPara();
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
