package com.twinkle.scaffold.common.data;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用返回
 * */
@Data
public class GeneralResult<T> implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -634083654601510733L;
    
    /**
     * 返回码
     * */
    @ApiModelProperty(value="结果码 100-通用成功")
    private String code;
    /**
     * 返回描述
     * */
    @ApiModelProperty(value="描述信息")
    private String desc;
    /**
     * 数据
     * */
    @ApiModelProperty(value="具体数据")
    private T data;
    
    public GeneralResult() {
    }
    
    public GeneralResult(String code) {
        this(code, null, null);
    }
    
    public GeneralResult(String code, String desc) {
        this(code, desc,null);
    }
    
    public GeneralResult(String code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }
    
    
}

