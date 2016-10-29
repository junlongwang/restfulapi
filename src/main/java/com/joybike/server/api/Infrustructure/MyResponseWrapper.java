package com.joybike.server.api.Infrustructure;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * Created by 58 on 2016/10/29.
 */
public class MyResponseWrapper extends HttpServletResponseWrapper {
    private MyWriter myWriter;
    //private MyOutputStream myOutputStream;

    private final Logger logger = Logger.getLogger(CorsFilter.class);

    public MyResponseWrapper(HttpServletResponse response) {
        super(response);
        try {
            myWriter = new MyWriter(super.getWriter());
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("初始化myWriter 异常：",e);
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException {
//        if(myWriter==null)
//        {
//            myWriter = new MyWriter(super.getWriter());
//        }
        return myWriter;
    }

//    @Override
//    public ServletOutputStream getOutputStream() throws IOException {
//        myOutputStream = new MyOutputStream(super.getOutputStream());
//        return myOutputStream;
//    }

    public MyWriter getMyWriter() {
        return myWriter;
    }

//    public MyOutputStream getMyOutputStream() {
//        return myOutputStream;
//    }

}
