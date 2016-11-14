package com.joybike.server.api.util;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by lishaoyong on 16/11/15.
 */
public class MyTaskAnnotation {

    private final Logger logger = Logger.getLogger(MyTaskAnnotation.class);

    /**
     * 定时计算。每天凌晨 01:00 执行一次
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void show(){
        logger.info("Annotation：is show run");
    }

    /**
     * 心跳更新。启动时执行一次，之后每隔2秒执行一次
     */
    @Scheduled(fixedRate = 1000*5)
    public void print(){
        logger.info("Annotation：print run");
    }

}
