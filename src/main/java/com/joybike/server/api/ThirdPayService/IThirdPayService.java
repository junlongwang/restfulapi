package com.joybike.server.api.ThirdPayService;
import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

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
}
