package com.twinkle.scaffold.config;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.common.constants.AspectConstant;
import com.twinkle.scaffold.common.constants.EventCode;
import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralEvent;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.common.data.sysevent.SysExceptionData;
import com.twinkle.scaffold.common.data.sysevent.SysTimeoutData;
import com.twinkle.scaffold.common.error.GeneralException;
import com.twinkle.scaffold.common.utils.AppEventUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * API 切面拦截
 * 
 */
@Component
@Aspect
@Order(AspectConstant.APIASPECT_ORDER)
@Slf4j
public class ApiAspect {

    private static final Long EXPECT_MAX_HANDLE_TIME = 2 * 1000L; // 2 s
    private static final String CONTROLLER_BASE_PACKAGE = "com.twinkle.scaffold.modules";
    
    /**
     * 打印输入输出及处理时间过长的日志 针对异常的统一处理
     */
    @Around("execution(public * " + CONTROLLER_BASE_PACKAGE + "..api..*.*(..))"
            + "|| execution(public * " + CONTROLLER_BASE_PACKAGE + "..task..*.*(..))")
    public Object aroundApi(ProceedingJoinPoint pjp) throws Throwable {
        // 放入一个tranceId 方便跟踪日志
        insertMDC();
        Object[] args = pjp.getArgs();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        log.info("class:{},methodName:{},args:{}", className, methodName, args);
        Long startTimeMillis = System.currentTimeMillis();
        Object resp = null;
        try {
            resp = pjp.proceed();
        } catch (Exception e) {
            MethodSignature ms = (MethodSignature) pjp.getSignature();
            Class returnType = ms.getMethod().getReturnType();
            // 对异常进行处理
            resp = handleException(returnType, e);
            // 发送一个系统异常事件
            try {
                publishSysExceptionEvent(className,methodName,e);
            } catch (Exception e2) {
                log.warn("publishSysEvent error,msg = {}",e2.getMessage());
            }
        }
        Long endTimeMillis = System.currentTimeMillis();
        Long spend = endTimeMillis - startTimeMillis;
        log.info("class:{},methodName:{},spend:{} ms,resp:{}", className, methodName, spend, resp);
        if (spend > EXPECT_MAX_HANDLE_TIME) {
            log.warn("class:{},methodName:{},spend:{}", className, methodName, spend);
            // 发送一个系统超时事件
            try {
                publishSysTimeoutEvent(className,methodName,spend);
            } catch (Exception e2) {
                log.warn("publishSysEvent error,msg = {}",e2.getMessage());
            }
        }
        return resp;
    }

    /**
     * 统一的异常处理
     */
    private GeneralResult handleException(Class returnType, Exception e) throws Throwable {
        GeneralResult errorResult = new GeneralResult();
        if (e instanceof GeneralException) {
            errorResult.setCode(((GeneralException) e).getCode());
            errorResult.setDesc(((GeneralException) e).getDesc());
            errorResult.setData(((GeneralException) e).getData());
        }else{
            errorResult.setCode(ResultCode.SERVER_ERROR);
            errorResult.setDesc(e.getMessage());
        }
        return errorResult;
    }
    
    /**
     * Thread LocalMap 中放入一个traceId
     * */
    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(AspectConstant.TRACE_ID, uniqueId);
        return true;
    }
    
    /**
     * 发送一个系统异常事件
     * */
    private void publishSysExceptionEvent(String classSimpleName,String methodName,Exception exception) {
        SysExceptionData sysExceptionData = new SysExceptionData();
        sysExceptionData.setClassSimpleName(classSimpleName);
        sysExceptionData.setMethodName(methodName);
        sysExceptionData.setException(exception);
        AppEventUtils.publishSpringEvent(new GeneralEvent(EventCode.SYS_API_ERROR,sysExceptionData));
    }
    
    /**
     * 发送一个系统超时事件
     * */
    private void publishSysTimeoutEvent(String classSimpleName,String methodName,long invokeTime) {
        SysTimeoutData sysTimeoutData = new SysTimeoutData();
        sysTimeoutData.setClassSimpleName(classSimpleName);
        sysTimeoutData.setMethodName(methodName);
        sysTimeoutData.setInvokeTime(invokeTime);
        AppEventUtils.publishSpringEvent(new GeneralEvent(EventCode.SYS_API_TIMEOUT,sysTimeoutData));
    }
}