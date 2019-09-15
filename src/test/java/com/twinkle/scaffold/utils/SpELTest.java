package com.twinkle.scaffold.utils;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.twinkle.scaffold.common.constants.EventCode;
import com.twinkle.scaffold.common.data.GeneralEvent;
import com.twinkle.scaffold.common.data.sysevent.SysExceptionData;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月18日 下午4:06:52 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class SpELTest {
    
    private ExpressionParser spelParser = new SpelExpressionParser();
    
    @Test
    public void generateKey() {
        SysExceptionData sysExceptionData = new SysExceptionData();
        sysExceptionData.setClassSimpleName("DemoController");
        sysExceptionData.setMethodName("test1");
        sysExceptionData.setException(null);
        GeneralEvent sysExceptionEvent = new GeneralEvent(EventCode.SYS_API_ERROR,sysExceptionData);
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("sysExceptionEvent", sysExceptionEvent);
        String el = "#sysExceptionEvent.data instanceof  T(com.twinkle.scaffold.common.data.sysevent.SysExceptionData)";
        Expression expression = spelParser.parseExpression(el);
        Object result = expression.getValue(context);
        System.out.println(result);
        System.out.println(result.getClass());
    }
}
