package com.twinkle.scaffold.common.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月4日 下午3:47:48 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class TemplateUtils {

    /**
     * 通过Velocity，使用str模板和参数，生成一个text
     * */
    public static String generateTextFromTemplate(String template,Map<String,Object> paramMap){
        Velocity.init();
        VelocityContext context = new VelocityContext(paramMap);
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(context, stringWriter,Thread.currentThread().getName(), template);
        return stringWriter.toString();
    }
    
}
