package com.twinkle.scaffold.common.data;

import java.io.Serializable;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.ToString;

/**
 * 通用事件 <br/>
 * Date:    2019年8月18日 下午2:49:15 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
public class GeneralEvent<T> implements Serializable{

    /**
     * 事件code
     * */
    private String code;
    /**
     * 事件发生地点
     * */
    private String happenAt;
    /**
     * 事件发生时间
     * */
    private Long timestamp;
    /**
     * traceId
     * */
    private String traceId;
    /**
     * 事件具体数据
     * */
    @NonNull
    private T data;
    
    public GeneralEvent(String code,T data){
        this(code,null,System.currentTimeMillis(),data);
    }
    
    public GeneralEvent(String code,String happenAt,T data){
        this(code,happenAt,System.currentTimeMillis(),data);
    }
    
    public GeneralEvent(String code,String happenAt,Long timestamp,T data){
        this.code = code;
        this.happenAt = happenAt;
        this.timestamp = timestamp;
        this.data = data;
    }
}
