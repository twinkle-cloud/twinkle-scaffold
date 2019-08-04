package com.twinkle.scaffold.client.msm;

import com.twinkle.scaffold.common.data.msm.GroupShortMsg;
import com.twinkle.scaffold.common.data.msm.SimpleShortMsg;

/**
 * mobile short message client <br/>
 * Date:    2019年8月4日 下午5:00:14 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public interface IMsmClient {

    /**
     * 发送点对点的短信
     * */
    public void sendSimpleMsm(SimpleShortMsg simpleShortMsg);
    
    /**
     * 发送群发短信
     * */
    public void sendGroupMsm(GroupShortMsg groupShortMsg);
}
