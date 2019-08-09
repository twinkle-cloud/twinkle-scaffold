package com.twinkle.scaffold.component.apiscy;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
 * 访问频率限制切面，控制API接口访问频率 <br/>
 * Date:    2019年8月8日 下午11:04:13 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Aspect
@Order(AspectConstant.APISCY_FREQLIMITASPECT_ORDER)
@Slf4j
public class FreqLimitAspect {

    private static final String KEY_PREFIX = "APISCY_FREQLIMIT:";
    private ExpressionParser spelParser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
    
    @Autowired
    @NonNull
    private RedisClient redisClient;
    
    @Before("@annotation(freqLimit)")
    public void beforeFreqLimit(JoinPoint joinPoint,FreqLimit freqLimit) throws Throwable {
        String key = generateKey(joinPoint,freqLimit);
        if(!redisClient.addLock(key,freqLimit.intervalTime(), 0)){
            throw new GeneralException(ResultCode.APISCY_FREQLIMIT,"接口访问频繁");
        }
    }
    
    /**
     * 根据SpEL表达式生成key
     * */
    private String generateKey(JoinPoint joinPoint,FreqLimit freqLimit){
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], args[len]);
        }
        // 一些特定的参数
        if(freqLimit.key().indexOf("#"+AspectConstant.APISCY_PARAM_CURRENTUSERNAME) >= 0){
            context.setVariable(AspectConstant.APISCY_PARAM_CURRENTUSERNAME, CurrentUserUtils.getCurrentUserName());
        }
        Expression expression = spelParser.parseExpression(freqLimit.key());
        String key = expression.getValue(context, String.class);
        return KEY_PREFIX+joinPoint.getTarget().getClass().getSimpleName()+"_"+method.getName()+":"+key;
    }
}
