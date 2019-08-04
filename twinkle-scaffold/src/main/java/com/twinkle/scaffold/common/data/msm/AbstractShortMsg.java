package com.twinkle.scaffold.common.data.msm;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * 抽象一个短信公共字段 <br/>
 * Date:    2019年8月4日 下午5:02:20 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
public abstract class AbstractShortMsg implements Serializable{

    protected String content;
    /**
     * 模板ID
     * 有些短信平台，需要短信模板ID
     * */
    protected String templateId;
    /**
     * 短信签名ID
     * 有些短信平台，需要短信签名
     * */
    protected String signId;
    /**
     * 参数map
     * 有些短信平台，需要paramMap
     */
    protected Map<String,Object> paramMap;
}
