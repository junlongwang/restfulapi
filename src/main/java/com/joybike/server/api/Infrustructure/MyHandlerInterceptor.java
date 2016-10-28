package com.joybike.server.api.Infrustructure;

import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

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

        String token = request.getHeader("Authentication");

        logger.info("+++++++++++++++++++++++++++");
        logger.info("Authentication="+token);
        logger.info("+++++++++++++++++++++++++++");

//        if(token==null || "".equals(token)) {
//            response.setContentType("text/html; charset=utf-8");
//            PrintWriter out = response.getWriter();
//            out.write(token + "非法请求！");
//            out.flush();
//            out.close();
//            return false;
//        }

//        String andrior = RedixUtil.getString("6cf6ead412df6ad88880c4742be3d2f6");
//        String ios = RedixUtil.getString("6566567b50be0ac7b85076ae76acbaa7");
//        String h5 = RedixUtil.getString("f3c75e05f57b4385bdf5f0a0812d996a");
//        boolean exits = (andrior.equals(token) || ios.equals(token) || h5.equals(token));
//        if(!exits) {
//            response.setContentType("text/html; charset=utf-8");
//            PrintWriter out = response.getWriter();
//            out.write(token + "非法请求！");
//            out.flush();
//            out.close();
//            return false;
//        }

//        Map<String, String> map = new HashMap<String, String>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//
//            //logger.info(key+" :  "+value);
//
////            if("Authentication".equals(key))
////            {
////                if(value=="B9A45EAC2C54BF5F8379C3D3A352A052" || value.equals("B9A45EAC2C54BF5F8379C3D3A352A052"))
////                {
////                    return true;
////                }
////            }
//        }
//        System.out.println(map);
        System.out.println("++++++++++++++++++++++++++++++++");

        logger.info(request.getRemoteAddr());//得到来访者IP
        logger.info(request.getRemoteHost());
        logger.info(request.getRequestURI());//得到请求URL地址

        response.setHeader("Access-Control-Allow-Origin", "*");
        return true;
    }

}
