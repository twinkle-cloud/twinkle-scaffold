package com.twinkle.scaffold.config;

import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.common.constants.AspectConstant;
import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.data.GeneralResult;
import com.twinkle.scaffold.common.error.GeneralException;

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
    private static final String TRACE_ID = "traceId";
    
    /**
     * 打印输入输出及处理时间过长的日志 针对异常的统一处理
     */
    @Around("execution(public * " + CONTROLLER_BASE_PACKAGE + "..api..*.*(..))")
    public Object aroundApi(ProceedingJoinPoint pjp) throws Throwable {
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
            resp = handleException(returnType, e);
        }
        Long endTimeMillis = System.currentTimeMillis();
        Long spend = endTimeMillis - startTimeMillis;
        log.info("class:{},methodName:{},spend:{} ms,resp:{}", className, methodName, spend, resp);
        if (spend > EXPECT_MAX_HANDLE_TIME) {
            log.warn("class:{},methodName:{},spend:{}", className, methodName, spend);
        }
        return resp;
    }

    /**
     * 统一的异常处理
     */
    private GeneralResult handleException(Class returnType, Exception e) throws Throwable {
        GeneralResult errorResult = new GeneralResult();
//        if (returnType.equals(GeneralResult.class)) {
//            errorResult = new GeneralResult();
//        } else {
//            throw e;
//        }
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
    
    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(TRACE_ID, uniqueId);
        return true;
    }
}