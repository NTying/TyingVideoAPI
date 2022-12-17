package com.tying.aspect;

import com.tying.annotation.SystemLog;
import com.tying.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * @author Tying
 * @version 1.0
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.tying.annotation.SystemLog)")
    public void pt() {
    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object ret;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("=======END=======" + System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret) {

        // 打印出参
        log.info("Response      : {}", JsonUtils.getJson(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        log.info("=======Start=======");

        // 获取封装了请求信息的 Request 对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取业务接口名称
        String businessName = getBusinessName(joinPoint);
        // 打印请求 URL
        log.info("URL           : {}", request.getRequestURL());
        // 打印业务接口描述信息
        log.info("BusinessName  : {}", businessName);
        // 打印 HTTP Method
        log.info("Http Method   : {}", request.getMethod());
        // 打印调用的 Controller 的全路径以及执行方法
        log.info("Class Method  : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
        // 打印请求的 IP
        log.info("IP            : {}", request.getRemoteHost());
        // 打印请求的入参
        log.info("Request Args  : {}", JsonUtils.getJson(joinPoint.getArgs()));
    }

    private String getBusinessName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String businessName = signature.getMethod().getAnnotation(SystemLog.class).businessName();
        return businessName;
    }
}
