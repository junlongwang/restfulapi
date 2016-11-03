package com.joybike.server.api.ThirdPayService;

import com.joybike.server.api.model.RedirectParam;
import com.joybike.server.api.model.ThirdPayBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by 58 on 2016/10/24.
 */
public interface AliPayConstructUrlInter {

    /**
     * 生成请求URL
     * @param paraMap
     * @return
     */
    RedirectParam getUrl(HashMap<String, String> paraMap);

    String callBack(HttpServletRequest request);

    /**
     * @param payBean
     * @return
     */
    String getRefundUrl(ThirdPayBean payBean);
}
