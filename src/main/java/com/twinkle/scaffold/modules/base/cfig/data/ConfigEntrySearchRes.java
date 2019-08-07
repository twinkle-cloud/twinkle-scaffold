package com.twinkle.scaffold.modules.base.cfig.data;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午10:19:40 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */

@Data
@ToString
public class ConfigEntrySearchRes implements Serializable{
    
    private String code;
    @ApiModelProperty(value="配置项结构类型1-列表,2-字典,3-单父节点树")
    private Byte itemStructureType;
    private Object value;
    
}
