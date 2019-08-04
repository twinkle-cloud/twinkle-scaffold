package com.twinkle.scaffold.component.cfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.twinkle.scaffold.AbstractTest;
import com.twinkle.scaffold.component.cfig.constants.CfigConstants;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigEntry;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigItem;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午8:42:08 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class ConfigCompTest extends AbstractTest{
    
    @Autowired
    private ConfigComp configComp;
    
    @Test
    public void getServiceMapConfigTest(){
        String entryCode = "MAP_1";
        String key = "k1";
        Map<String, String> mapConfig = configComp.getServiceMapConfig(entryCode);
        System.out.println(JSONObject.toJSONString(mapConfig));
        System.out.println(configComp.getServiceMapConfig(entryCode,key));
        
        
    }
}
