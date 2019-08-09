package com.twinkle.scaffold.common.constants;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月23日 下午9:14:25 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class ResultCode {

    /**
     * 操作成功
     * */
    public static String OPERATE_SUCCESS = "100";
    /**
     * 没有当前用户
     * */
    public static String NO_CURRENT_USER = "101";
    /**
     * 参数无效
     * */
    public static String PARAM_INVALID = "102";
    /**
     * 权限不合法
     * */
    public static String AUTH_FAILD = "401";
    /**
     * 系统异常
     * */
    public static String SERVER_ERROR = "500";
    
    
    /**
     * 接口访问超过访问间隔限制
     * */
    public static String APISCY_FREQLIMIT = "1001";
    public static String APISCY_LOCKLIMIT = "1002";
}
