package com.joybike.server.api.util;

import static java.lang.Math.cos;

/**
 * Created by lishaoyong on 16/10/23.
 */
public class UnixGps {

    /**
     * 距离转换成经度
     * @param distance
     * @return
     */
    public static Double doLngDegress(double distance) {
        double lngDegree = 2 * Math.asin(Math.sin((double)distance/12742)/Math.cos(distance));
        // 转换弧度
        lngDegree = lngDegree * (180/Math.PI);
        return lngDegree;
    }

    /**
     * 距离转换成纬度
     * @param distance
     * @return
     */
    public static Double doLatDegress(double distance) {
        double latDegrees = (double)distance/6371;
        // 转换弧度
        latDegrees = latDegrees * (180/Math.PI);
        return latDegrees;
    }

    public static void main(String args[]){
        //40.0277080598,116.3552789208
        double a = doLngDegress(40.02);
        System.out.println(a);

        double b = doLatDegress(116.35);
        System.out.println(b);
    }
}
