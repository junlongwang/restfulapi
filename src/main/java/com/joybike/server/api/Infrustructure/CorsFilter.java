package com.joybike.server.api.Infrustructure;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.Filter;
/**
 * Created by 58 on 2016/10/28.
 */
public class CorsFilter implements Filter {

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
//            // CORS "pre-flight" request
//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//            response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, sid, mycustom, smuser");
//            response.addHeader("Access-Control-Max-Age", "1800");//30 min
//        }
//        filterChain.doFilter(request, response);
//    }

    private final Logger logger = Logger.getLogger(CorsFilter.class);

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
//        MyResponseWrapper responseWrapper = new MyResponseWrapper((HttpServletResponse) response);
//        response.reset();
//        responseWrapper.reset();
//       MyWriter writer = responseWrapper.getMyWriter();
//        chain.doFilter(request, responseWrapper);
////        response.setContentType("text/plain");
////        responseWrapper.setHeader("Pragma", "No-cache");
////        responseWrapper.setHeader("Cache-Control", "no-cache");
////        responseWrapper.setDateHeader("Expires", 0);
////        response.reset();
////        responseWrapper.reset();
////        MyWriter writer = responseWrapper.getMyWriter();
//        if (writer != null) {
//            String content = writer.getContent();
//            content = content.replace("$replace$", "This is replaced!");
//            response.getWriter().write(content);
//
//            logger.info("======================================================");
//            logger.info(content);
//            logger.info("======================================================");
//        }
        chain.doFilter(request, response);
        Object object = H5Result.getDataSource();
        if(object!=null) {
            ResponseEntity responseEntity = null;
            try {
                responseEntity = (ResponseEntity) object;
            } catch (Exception e) {
                logger.error("(ResponseEntity)object转化异常：", e);
            }

            String result = JSON.toJSONString(responseEntity.getBody());

            logger.info("====================responseEntity==================================");
            logger.info("jsonnpcallback(" + result + ")");
            logger.info("======================responseEntity================================");
            String responseContent = result;
            try {
                //response.setContentType("text/plain");

                String jsonpCallback = request.getParameter("callback");//客户端请求参数
                if(jsonpCallback!=null && !jsonpCallback.equals("")) {
                    PrintWriter out = response.getWriter();
                    if (!jsonpCallback.equals("?")) {
                        //out.println(jsonpCallback + "(" + responseContent + ")");//返回jsonp格式数据
                        out.write(jsonpCallback + "(" + responseContent + ")");
                    } else {
                        //out.println("jsonpCallback(" + responseContent + ")");//返回jsonp格式数据
                        out.write(jsonpCallback + "(" + responseContent + ")");
                    }
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
