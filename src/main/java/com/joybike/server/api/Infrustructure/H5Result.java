package com.joybike.server.api.Infrustructure;
import org.springframework.http.ResponseEntity;
/**
 * Created by 58 on 2016/10/29.
 */
public class H5Result {

    private static final ThreadLocal<Object> contextHolder = new ThreadLocal<Object>();

    public static void setDataSource(Object responseEntity) {
        if (responseEntity == null) {
            contextHolder.set(responseEntity);
        } else {
            contextHolder.set(responseEntity);
        }
    }

    public static Object getDataSource() {
        try {
            return contextHolder.get();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void clear() {
        contextHolder.remove();
    }
}
