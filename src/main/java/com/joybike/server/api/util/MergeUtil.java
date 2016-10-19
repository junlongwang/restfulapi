package com.joybike.server.api.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Created by lishaoyong on 16/10/19.
 */
public class MergeUtil {
    
    public static <M> void merge(M target, M destination) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod().invoke(destination);

                // Only copy values values where the destination values is null
                if (originalValue == null) {
                    Object defaultValue = descriptor.getReadMethod().invoke(target);

                    descriptor.getWriteMethod().invoke(destination, defaultValue);
                }

            }
        }
    }
}
