package com.twinkle.scaffold.common.data.email;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月4日 下午2:50:04 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
public class SimpleEmail implements Serializable{

    protected String from;
    protected List<String> to;
    protected List<String> bcc;
    
    protected String subject;
    protected String text;
    protected Boolean html;
    protected List<SimpleAttachment> attachments;
    
    public void setText(String text,Boolean html){
        this.text = text;
        this.html = html;
    }
}
