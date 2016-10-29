package com.joybike.server.api.Infrustructure;


import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.Writer;
/**
 * Created by 58 on 2016/10/29.
 */
public class MyWriter extends PrintWriter{
    private StringBuilder buffer;

    private final Logger logger = Logger.getLogger(CorsFilter.class);

    public MyWriter(Writer out) {
        super(out);
        buffer = new StringBuilder();
    }

    @Override
    public void write(char[] buf, int off, int len) {
        // super.write(buf, off, len);
        char[] dest = new char[len];
        System.arraycopy(buf, off, dest, 0, len);
        buffer.append(dest);
        System.out.println("write1");
        logger.info("write1 "+dest);
    }

    @Override
    public void write(char[] buf) {
        super.write(buf);
        System.out.println("write2");
        logger.info("write2 " + buf);
    }

    @Override
    public void write(int c) {
        super.write(c);
        System.out.println("write3");
        logger.info("write3 " + c);
    }

    @Override
    public void write(String s, int off, int len) {
        super.write(s, off, len);
        System.out.println("write4");
        logger.info("write4 " + s);
    }

    @Override
    public void write(String s) {
        super.write(s);
        System.out.println("write5");

        logger.info("write5 " + s);
    }

    public String getContent(){
        return buffer.toString();
    }

}
