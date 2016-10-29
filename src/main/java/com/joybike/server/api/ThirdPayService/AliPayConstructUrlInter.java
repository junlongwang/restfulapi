package com.joybike.server.api.ThirdPayService;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by 58 on 2016/10/24.
 */
public interface AliPayConstructUrlInter {
    public RedirectParam getUrl(HashMap<String,String> paraMap);

    public String callBack(HttpServletRequest request);

    /**
     *
     * @param payBean
     * @return
     */
    public String getRefundUrl(ThirdPayBean payBean);
}
