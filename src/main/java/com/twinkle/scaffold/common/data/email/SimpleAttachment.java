package com.twinkle.scaffold.common.data.email;

import java.io.Serializable;

import org.springframework.core.io.InputStreamSource;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月4日 下午3:10:01 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
public class SimpleAttachment implements Serializable{

    protected String name;
    protected InputStreamSource inputStreamSource;
    protected String contentType;
    
}
