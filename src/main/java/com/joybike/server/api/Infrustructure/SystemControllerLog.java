package com.joybike.server.api.Infrustructure;

/**
 * Created by 58 on 2016/10/29.
 */

import java.lang.annotation.*;

/**
 *自定义注解 拦截Controller
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface SystemControllerLog {

    String description()  default "";


}
