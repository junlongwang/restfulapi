package com.joybike.server.api.Infrustructure;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截HTTP请求
 * Created by 58 on 2016/10/14.
 */
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    Logger logger =Logger.getLogger(MyHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //PermissionMeta
        HandlerMethod method = (HandlerMethod)handler;
        System.out.println(method);
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);

            //logger.info(key+" :  "+value);

//            if("Authentication".equals(key))
//            {
//                if(value=="B9A45EAC2C54BF5F8379C3D3A352A052" || value.equals("B9A45EAC2C54BF5F8379C3D3A352A052"))
//                {
//                    return true;
//                }
//            }
        }
        System.out.println(map);
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println(request.getRequestURI());
        return true;
    }

}
