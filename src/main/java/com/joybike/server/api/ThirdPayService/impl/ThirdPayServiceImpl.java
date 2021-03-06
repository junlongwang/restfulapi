package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;

import com.joybike.server.api.ThirdPayService.WxappConstructUrlInter;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;
import com.joybike.server.api.model.WxNotifyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by LongZiyuan on 2016/10/20.
 */
@Service
public class ThirdPayServiceImpl implements ThirdPayService {

    @Autowired
    private WxPublicConstructUrlInter wxPublicConstructUrlInter;

    @Autowired
    private WxappConstructUrlInter wxappConstructUrlInter;

    @Autowired
    private AliPayConstructUrlInter aliPayConstructUrlInter;


    private String wxAppmch_id = "1407599302";
    private String wxPubmch_id = "1401808502";

    @Override
    public String execute(ThirdPayBean payOrder) {
        //payorderUtil
        if(payOrder.getChannelId() == PayType.weixin.getValue())
        {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("out_trade_no", payOrder.getId().toString());
            map.put("total_fee", payOrder.getOrderMoney().toString());
            map.put("spbill_create_ip",payOrder.getOperIP());
            map.put("body",payOrder.getPruductDesc());
            map.put("attach",String.valueOf(payOrder.getChannelId()));
            if (String.valueOf(payOrder.getCosumeid()) != null){
                map.put("attach",String.valueOf(payOrder.getCosumeid()));
            }
            RedirectParam redirectParam= wxappConstructUrlInter.getUrl(map);
            if( redirectParam != null )
                return redirectParam.getPara();
        }
        else if(payOrder.getChannelId() == PayType.weixinpublic.getValue()){
            RedirectParam redirectParam = wxPublicConstructUrlInter.getUrl(payOrder);
            if (redirectParam != null)
                return redirectParam.getPara();
        }
        else{
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("out_trade_no", payOrder.getId().toString());
            map.put("total_fee", payOrder.getOrderMoney().toString());
            if(payOrder.getCosumeid() != null)
                map.put("consumeid", String.valueOf(payOrder.getCosumeid()));
            //map.put("body",String.valueOf(payOrder.getCosumeid()));
            map.put("subject",payOrder.getPruductDesc());
            map.put("body",payOrder.getPruductDesc());
            map.put("it_b_pay", "15d"); //超时时间
            RedirectParam redirectParam= aliPayConstructUrlInter.getUrl(map);
            if( redirectParam != null )
                return redirectParam.getPara();
        }
        return "";
    }


    public static void main(String[] args) {
        ThirdPayBean payBean = new ThirdPayBean();
        payBean.setId(Long.valueOf("12312321321312312"));
        payBean.setRechargeType(1);
        payBean.setChannelId(117);
        payBean.setPruductDesc("joybike押金充值");
        payBean.setOrderMoney(BigDecimal.valueOf(299));
        payBean.setOperIP("192.168.0.1");
        ThirdPayServiceImpl thirdPayService = new ThirdPayServiceImpl();
        thirdPayService.execute(payBean);
    }

    @Override
    public String queryPayResult(ThirdPayBean payOrder) {
        String queryResult = "";
        if (payOrder != null) {
            //wxappConstructUrlInter
            //WxappConstructUrlInter cui = new WxappConstructUrlImpl();
        }
        return queryResult;
    }

    @Override
    public String callBack(WxNotifyOrder wxNotifyOrder){
        String mch_id = wxNotifyOrder.getMch_id();
        if (mch_id.equals(wxAppmch_id)){
            return wxappConstructUrlInter.callBack(wxNotifyOrder);
        }
        else if(mch_id.equals(wxPubmch_id)){
            return new WxPublicConstructUrlImpl().callBack(wxNotifyOrder);
        } else{
            return "";
        }
    }

    /**
     * 执行退款请求
     * @param payBean
     * @return
     */
    @Override
    public String executeRefund(ThirdPayBean payBean){
        String result = "fail";
        if(payBean.getChannelId() == PayType.weixin.getValue())
        {
            result = wxappConstructUrlInter.getRefundUrl(payBean);
        }
        else if(payBean.getChannelId() == PayType.weixinpublic.getValue()){
            result = wxPublicConstructUrlInter.getRefundUrl(payBean);
        }
        else{
            result = aliPayConstructUrlInter.getRefundUrl(payBean);
        }
        return result;
    }
}
