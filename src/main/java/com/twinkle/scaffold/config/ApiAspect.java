package com.twinkle.scaffold.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

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
@Slf4j
public class ApiAspect {

    private final static Long EXPECT_MAX_HANDLE_TIME = 2 * 1000L; // 2 s
    private final static String CONTROLLER_BASE_PACKAGE = "com.twinkle.scaffold.modules";

    @Pointcut("execution(public * " + CONTROLLER_BASE_PACKAGE + "..api..*.*(..))")
    public void api() {
    }

    /**
     * 打印输入输出及处理时间过长的日志 针对异常的统一处理
     */
    @Around("api()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        String className = pjp.getTarget().getClass().getName();
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
        GeneralResult errorResult = null;
        if (returnType.equals(GeneralResult.class)) {
            errorResult = new GeneralResult();
        } else {
            throw e;
        }
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
}