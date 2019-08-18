package com.twinkle.scaffold.common.data.sysevent;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月18日 下午2:58:26 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
public class SysTimeoutData {

    private String classSimpleName;
    
    private String methodName;
    
    private String traceId;
    
    private Long invokeTime;
}
