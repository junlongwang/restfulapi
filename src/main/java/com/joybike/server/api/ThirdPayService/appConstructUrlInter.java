package com.joybike.server.api.ThirdPayService;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

import java.util.HashMap;

/**
 * Created by 58 on 2016/10/20.
 */
public interface appConstructUrlInter {
    /**
     *
     * @param paraMap
     * @return
     */
    public RedirectParam getUrl(HashMap<String, String> paraMap);
}
