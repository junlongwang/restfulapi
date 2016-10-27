package com.joybike.server.api.ThirdPayService;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by 58 on 2016/10/20.
 */
public interface WxappConstructUrlInter {
    /**
     *
     * @param paraMap
     * @return
     */
    public RedirectParam getUrl(HashMap<String, String> paraMap);

    /**
     * 支付回调请求
     * @param request
     * @return
     */
    public String callBack(HttpServletRequest request);

    /**
     *
     * @param payBean
     * @return
     */
    public String getRefundUrl(ThirdPayBean payBean);
}
