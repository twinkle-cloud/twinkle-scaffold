package com.twinkle.scaffold.component.apiscy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问频率限制注解，对高耗能接口的限流  <br/>
 * Date:    2019年8月8日 下午11:07:51 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface FreqLimit {
    
    /**
     * 频率限制的标识,仅支持SpEL表达式<br/>
     * #currentUserName,仅支持受限的资源接口
     * @see https://baijiahao.baidu.com/s?id=1618293103449832238&wfr=spider&for=pc
     * */
    String key();
    
    /**
     * 距离上次访问的间隔,单位毫秒
     * */
    long intervalTime();
}
