package com.twinkle.scaffold.common.data.msm;

import lombok.Data;
import lombok.ToString;

/**
 * 简单的手机短信 ，适合于点对点发送 <br/>
 * Date:    2019年8月4日 下午5:01:21 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
public class SimpleShortMsg extends AbstractShortMsg{

    private String targetMobile;
    
}
