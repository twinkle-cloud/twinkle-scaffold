package com.twinkle.scaffold.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring主要负责，与spring容器交互 <br/>
 * Date:    2019年8月18日 下午2:31:10 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Slf4j
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
    
    /**
     * 根据类，从spring容器中，获取唯一的实例
     * */
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
    
    /**
     * 根据类和实例名，从spring容器中，获取唯一的实例
     * */
    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }
    
}
