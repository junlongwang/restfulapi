package com.joybike.server.api.ThirdPayService;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by LongZiyuan on 2016/10/20.
 */
public interface IThirdPayService {
    /**
     * 执行支付请求
     * @param payOrder
     */
    public String execute(ThirdPayBean payOrder);

    /**
     * 查询支付结果
     * @param payOrder
     * @return
     */
    public String queryPayResult(ThirdPayBean payOrder);

    /**
     * 支付回调请求
     * @param request
     * @return
     */
    public String callBack(HttpServletRequest request);
}
