package com.twinkle.scaffold.component.apiscy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对一些不能进行并发操作的业务接口，设计了该注解 <br/>
 * 当访问该这类接口时，会加锁，接口返回或异常后，释放锁<br/>
 * 或者当接口长时间未返回，当到达 期望锁定时间 后，也会释放锁<br/>
 * 期望锁定时间 默认1小时<br/>
 * Date:    2019年8月9日 下午11:36:50 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface LockLimit {

    /**
     * 锁标识,仅支持SpEL表达式<br/>
     * #currentUserName,仅支持受限的资源接口
     * @see https://baijiahao.baidu.com/s?id=1618293103449832238&wfr=spider&for=pc
     * */
    String key();
    
    /**
     * 期望锁定时间，单位毫秒<br/>，
     * 当接口调用完成后，锁会立即释放
     * 期望锁定时间 默认1小时
     * */
    long expectedLockTime() default 3600*1000;
}
