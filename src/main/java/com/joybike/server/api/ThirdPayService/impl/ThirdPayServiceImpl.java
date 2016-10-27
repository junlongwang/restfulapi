package com.joybike.server.api.ThirdPayService.impl;

import com.joybike.server.api.Enum.PayType;
import com.joybike.server.api.ThirdPayService.AliPayConstructUrlInter;
import com.joybike.server.api.ThirdPayService.ThirdPayService;
import com.joybike.server.api.ThirdPayService.WxPublicConstructUrlInter;
import com.joybike.server.api.ThirdPayService.WxappConstructUrlInter;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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


    private String wxAppmch_id = "1404387302";
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
            map.put("attach",String.valueOf(payOrder.getChannelId()));
            if (String.valueOf(payOrder.getCosumeid()) != null && String.valueOf(payOrder.getCosumeid()) != ""){
                map.put("attach",String.valueOf(payOrder.getCosumeid()));
            }
            String channleId = String.valueOf(payOrder.getChannelId());
            map.put("attach",channleId);
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
            map.put("body",String.valueOf(payOrder.getCosumeid()));
            map.put("subject",payOrder.getPruductDesc());
            map.put("it_b_pay", "3d"); //超时时间
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
            WxappConstructUrlInter cui = new WxappConstructUrlImpl();
        }
        return queryResult;
    }

    @Override
    public String callBack(HttpServletRequest request){
        String mch_id = request.getParameter("mch_id");
        if (mch_id.equals(wxAppmch_id)){
            return new WxappConstructUrlImpl().callBack(request);
        }
        else if(mch_id.equals(wxPubmch_id)){
            return new WxPublicConstructUrlImpl().callBack(request);
        }else{
            return new AliPayConstructUrlImpl().callBack(request);
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

        }
        return result;
    }
}
