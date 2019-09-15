package com.twinkle.scaffold.common.constants;

/**
 * 切面常量  <br/>
 * Date:    2019年8月8日 下午10:37:25 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class AspectConstant {

    /**
     * API 公共切面
     * */
    public static final int APIASPECT_ORDER = 1;
    
    /**
     * APISCY 频率限制切面
     * */
    public static final int APISCY_FREQLIMITASPECT_ORDER = 2;
    /**
     * APISCY 锁限制切面
     * */
    public static final int APISCY_LOCKIMITASPECT_ORDER = 3;
    
    public static final String APISCY_PARAM_CURRENTUSERNAME = "currentUserName";
    
    public static final String TRACE_ID = "traceId";
}
