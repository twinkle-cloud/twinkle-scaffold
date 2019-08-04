package com.twinkle.scaffold.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.twinkle.scaffold.common.utils.TemplateUtils;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月4日 下午3:37:24 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class TemplateUtilsTest {

    @Test
    public void generateTextFromTemplateTest(){
        String template = "We are using $project $name to render this. $now";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name", "Velocity");
        paramMap.put("project", "Jakarta");
        paramMap.put("now", new Date());
        String text = TemplateUtils.generateTextFromTemplate(template, paramMap);
        System.out.println(text);
    }
}
