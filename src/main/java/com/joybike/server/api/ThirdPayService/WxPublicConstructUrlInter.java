package com.joybike.server.api.ThirdPayService;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by LongZiyuan on 2016/10/24.
 */
public interface WxPublicConstructUrlInter {
    /**
     *
     * @param payOrder
     * @return
     */
    public RedirectParam getUrl(ThirdPayBean payOrder);

    /**
     * 支付回调请求
     * @param request
     * @return
     */
    public String callBack(HttpServletRequest request);
}
