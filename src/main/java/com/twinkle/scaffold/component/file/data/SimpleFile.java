package com.twinkle.scaffold.component.file.data;

import java.io.InputStream;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月10日 下午5:32:57 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
public class SimpleFile implements Serializable {

    private String id;
    private String name;
    private long size;
    private String contentType;
    private InputStream inputStream;
    
}
