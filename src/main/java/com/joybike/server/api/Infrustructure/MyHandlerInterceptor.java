package com.joybike.server.api.Infrustructure;

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
        }
        System.out.println(map);
        System.out.println("++++++++++++++++++++++++++++++++");
        System.out.println(request.getRequestURI());
        return true;
    }

}
