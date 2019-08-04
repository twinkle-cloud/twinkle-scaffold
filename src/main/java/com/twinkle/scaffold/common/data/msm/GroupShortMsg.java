package com.twinkle.scaffold.common.data.msm;

import java.util.List;

import lombok.Data;

/**
 * 群发短信 <br/>
 * Date:    2019年8月4日 下午5:08:52 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
public class GroupShortMsg extends AbstractShortMsg{

    private List<String> targetMobileList;
}
