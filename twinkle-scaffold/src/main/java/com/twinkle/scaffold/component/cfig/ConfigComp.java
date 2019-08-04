package com.twinkle.scaffold.component.cfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twinkle.scaffold.component.cfig.constants.CfigConstants;
import com.twinkle.scaffold.component.cfig.repo.ConfigEntryRepo;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigEntry;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigItem;

/**
 * 配置管理组件 <br/>
 * Date:    2019年8月3日 下午8:42:08 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
public class ConfigComp {
    
    @Autowired
    private ConfigEntryRepo configEntryRepo;
    
    /**
     * 查询 Service map类型 的配置
     * */
    public Map<String,String> getServiceMapConfig(String entryCode){
        Optional<ConfigEntry> configEntry = configEntryRepo.findByCodeAndItemStructureTypeAndScopeContaining(entryCode,CfigConstants.CONFIGENTRY_ITEMSTRUCTURETYPE_MAP,CfigConstants.CONFIGENTRY_SCOPE_SERVICE);
        if(!configEntry.isPresent()){
            return null;
        }
        Map<String,String> result = new LinkedHashMap<>();
        if(CollectionUtils.isNotEmpty(configEntry.get().getConfigItemList())){
            for (ConfigItem configItem : configEntry.get().getConfigItemList()) {
                result.put(configItem.getKey(),configItem.getValue());
            }
        }
        return result;
    }
    
    /**
     * 查询 Service map类型 的配置 的配置项
     * */
    public String getServiceMapConfig(String entryCode,String key){
        Map<String,String> mapConfig = getServiceMapConfig(entryCode);
        if(mapConfig == null){
            return null;
        }
        return mapConfig.get(key);
    }
}
