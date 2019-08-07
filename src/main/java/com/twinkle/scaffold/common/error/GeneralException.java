package com.twinkle.scaffold.common.error;

import lombok.Data;
import lombok.ToString;

/**
 * 通用异常
 * */
@Data
@ToString
public class GeneralException extends RuntimeException{

    /**
     * 异常码
     * */
    private String code;
    /**
     * 异常描述
     * */
    private String desc;
    /**
     * 数据
     * */
    private Object data;
    
    public GeneralException(String code) {
        this(code, null, null);
    }
    
    public GeneralException(String code, String desc) {
        this(code, desc,null);
    }
    
    public GeneralException(String code, String desc, Object data) {
        super(code);
        this.code = code;
        this.desc = desc;
        this.data = data;
    }
    
}
