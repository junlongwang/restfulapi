package com.joybike.server.api.util;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.HashMap;

/**
 * Created by lishaoyong on 16/11/2.
 */
public class ToHashMap {

    public static HashMap<String, String> beanToMap(Object obj) {
        HashMap<String, String> params = new HashMap<String, String>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
