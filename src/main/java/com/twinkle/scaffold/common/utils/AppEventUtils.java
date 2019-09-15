package com.twinkle.scaffold.common.utils;

import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.common.constants.AspectConstant;
import com.twinkle.scaffold.common.data.GeneralEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务事件工具类 <br/>
 * 主要用于发布事件 <br/>
 * Date:    2019年9月15日 下午7:53:10 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Slf4j
public class AppEventUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
    
    public static void publishSpringEvent(GeneralEvent event){
        event.setTraceId(MDC.get(AspectConstant.TRACE_ID));
        applicationContext.publishEvent(event);
    }
}
