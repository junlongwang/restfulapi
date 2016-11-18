package com.joybike.server.api.Infrustructure;

import com.joybike.server.api.thirdparty.aliyun.redix.RedixUtil;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 拦截HTTP请求
 * Created by 58 on 2016/10/14.
 */
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    Logger logger =Logger.getLogger(MyHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            //PermissionMeta
            HandlerMethod method = (HandlerMethod) handler;
            System.out.println(method);
            String token = request.getHeader("Authentication");

            logger.info("+++++++++++++++++++++++++++");
            logger.info("Authentication=" + token);
            logger.info(method.getMethod().getName());
            logger.info("+++++++++++++++++++++++++++");
            logger.info("====================="+request.getRequestURI());
            //获取TOKEN，不添加入权限
            if("/restful/platform/getToken".equals(request.getRequestURI()))
            {
                return true;
            }
            //车锁GPS数据上传，不添加入权限
            if("/restful/post".equals(request.getRequestURI()))
            {
                return true;
            }

        if(token==null || "".equals(token)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write("{" +
                    "\"success\": false," +
                    "\"errorCode\": 1000," +
                    "\"errorMessage\":\"非法请求\"" +
                    "}");
            out.flush();
            out.close();
            return false;
        }
//
        String andrior = RedixUtil.getString("6cf6ead412df6ad88880c4742be3d2f6");
        String ios = RedixUtil.getString("6566567b50be0ac7b85076ae76acbaa7");
        String h5 = RedixUtil.getString("f3c75e05f57b4385bdf5f0a0812d996a");
        boolean exits = (token.equals(andrior) || token.equals(ios) || token.equals(h5));
        if(!exits) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write("{" +
                    "\"success\": false," +
                    "\"errorCode\": 1000," +
                    "\"errorMessage\":\"非法请求\"" +
                    "}");
            out.flush();
            out.close();
            return false;
        }

        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);

            logger.info(key+" :  "+value);

//            if("Authentication".equals(key))
//            {
//                if(value=="B9A45EAC2C54BF5F8379C3D3A352A052" || value.equals("B9A45EAC2C54BF5F8379C3D3A352A052"))
//                {
//                    return true;
//                }
//            }
        }
//        System.out.println(map);


            System.out.println("++++++++++++++++++++++++++++++++");

            logger.info(request.getRemoteAddr());//得到来访者IP
            logger.info(request.getRemoteHost());
            logger.info(request.getRequestURI());//得到请求URL地址
        }
        catch (Exception e)
        {
            logger.error("发生异常",e);//
            return false;
        }
        //response.setHeader("Access-Control-Allow-Origin", "*");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
// TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");


//        String responseContent = null;
//        try {
//            response.setContentType("text/plain");
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//            PrintWriter out = response.getWriter();
//
//            String jsonpCallback = request.getParameter("callbake");//客户端请求参数
//            out.println(jsonpCallback+"("+responseContent+")");//返回jsonp格式数据
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
