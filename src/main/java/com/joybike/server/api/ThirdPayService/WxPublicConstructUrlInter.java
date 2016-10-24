package com.joybike.server.api.ThirdPayService;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

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
}
