package com.joybike.server.api.Infrustructure;

/**
 * Created by 58 on 2016/10/29.
 */

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切点类
 * @author tiangai
 * @since 2014-08-05 Pm 20:35
 * @version 1.0
 */
@Aspect
@Component
public  class SystemLogAspect {
    //本地异常日志记录对象
    private final Logger logger = Logger.getLogger(SystemLogAspect.class);

    //Controller层切点
    @Pointcut("@annotation(com.joybike.server.api.Infrustructure.SystemControllerLog)")
    public  void controllerAspect() {
    }

    @Pointcut("target(com.joybike.server.api.restful.UserRestfulApi)")
    public void thriftServiceTarget() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public  void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求的IP
        String ip = request.getRemoteAddr();
        try {
            //*========控制台输出=========*//
            logger.info("=====前置通知开始=====");
            logger.info("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.info("方法描述:" + getControllerMethodDescription(joinPoint));
            logger.info("请求IP:" + ip);
            logger.info("=====前置通知结束=====");
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息:",e);
        }
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取请求ip
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = JSON.toJSONString(joinPoint.getArgs());
        try {
              /*========控制台输出=========*/
            logger.error("=====异常通知开始=====");
            logger.error("异常代码:" + e.getClass().getName());
            logger.error("异常信息:" + e.getMessage());
            logger.error("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.error("方法描述:" + getControllerMethodDescription(joinPoint));
            logger.error("请求IP:" + ip);
            logger.error("请求参数:" + params);
            logger.error("=====异常通知结束=====");
        }  catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:", ex);
        }
         /*==========记录本地异常日志==========*/
        //logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);

    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog. class).description();
                    break;
                }
            }
        }
        return description;
    }

    @Around("thriftServiceTarget()")
    public Object logProcessInfo(ProceedingJoinPoint pj) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = pj.getArgs();
        MethodSignature signature = (MethodSignature) pj.getSignature();
        try {
            Object ret = pj.proceed();
            log(start, signature.getName(), JSON.toJSONString(args), true, JSON.toJSONString(ret));
            H5Result.setDataSource(ret);
            return ret;
        } catch (Throwable e) {
            log(start, signature.getName(), JSON.toJSONString(args), false, e.getMessage());
            logger.error(signature.getName()+"请求出错了：",e);
            throw e;
        }
    }

    private void log(long start, String interfaceName, String paramInfo, boolean isNormal, String msg) {
        logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$");
        logger.info(String.format("{%s} {%d} {%s} {%s} \"{%s}\"", interfaceName, System.currentTimeMillis() - start, isNormal ? "OK" : "ERROR", paramInfo, msg));
        logger.info("$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
}
