package com.twinkle.scaffold.component.apiscy;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.client.redis.RedisClient;
import com.twinkle.scaffold.common.constants.AspectConstant;
import com.twinkle.scaffold.common.constants.ResultCode;
import com.twinkle.scaffold.common.error.GeneralException;
import com.twinkle.scaffold.common.utils.CurrentUserUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月9日 下午11:44:11 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Aspect
@Order(AspectConstant.APISCY_LOCKIMITASPECT_ORDER)
@Slf4j
public class LockLimitAspect {

    private static final String KEY_PREFIX = "APISCY_LOCKLIMIT:";
    private ExpressionParser spelParser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    
    @Autowired
    @NonNull
    private RedisClient redisClient;
    
    @Around("@annotation(lockLimit)")
    public Object aroundApi(ProceedingJoinPoint pjp,LockLimit lockLimit) throws Throwable {
        String key = generateKey(pjp,lockLimit);
        if(!redisClient.addLock(key,lockLimit.expectedLockTime(), 0)){
            throw new GeneralException(ResultCode.APISCY_LOCKLIMIT,"接口加锁失败");
        }
        try {
            Object result = pjp.proceed();
            redisClient.removeKeys(key);
            return result;
        } catch (Exception e) {
            redisClient.removeKeys(key);
            throw e;
        }
    }

    /**
     * @param pjp
     * @param lockLimit
     * @return
     */
    private String generateKey(ProceedingJoinPoint pjp, LockLimit lockLimit) {
        Object[] args = pjp.getArgs();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        // 一些特定的参数
        if(lockLimit.key().indexOf("#"+AspectConstant.APISCY_PARAM_CURRENTUSERNAME) >= 0){
            context.setVariable(AspectConstant.APISCY_PARAM_CURRENTUSERNAME, CurrentUserUtils.getCurrentUserName());
        }
        Expression expression = spelParser.parseExpression(lockLimit.key());
        String key = expression.getValue(context, String.class);
        return KEY_PREFIX+pjp.getTarget().getClass().getSimpleName()+"_"+method.getName()+":"+key;
    }
}
