package com.twinkle.scaffold.modules.base.cfig.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.twinkle.scaffold.component.cfig.constants.CfigConstants;
import com.twinkle.scaffold.component.cfig.repo.ConfigEntryRepo;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigEntry;
import com.twinkle.scaffold.component.cfig.repo.domain.ConfigItem;
import com.twinkle.scaffold.modules.base.cfig.data.ConfigEntrySearchRes;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午10:14:56 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class CfigService {
    
    @Autowired
    private ConfigEntryRepo configEntryRepo;
    
    /**
     * 根据codeList 和 scope 获取配置信息
     * */
    public List<ConfigEntrySearchRes> getConfigEntrysByCodes(List<String> entryCodeList,String scope){
        List<ConfigEntrySearchRes> result = new ArrayList<>();
        List<ConfigEntry> configEntryList = configEntryRepo.findByCodeInAndScopeContainingOrderByCodeAsc(entryCodeList,scope);
        if(CollectionUtils.isNotEmpty(configEntryList)){
            for (ConfigEntry configEntry : configEntryList) {
                ConfigEntrySearchRes configEntrySearchRes = new ConfigEntrySearchRes();
                configEntrySearchRes.setCode(configEntry.getCode());
                configEntrySearchRes.setItemStructureType(configEntry.getItemStructureType());
                configEntrySearchRes.setValue(formatConfigItems(configEntry));
                result.add(configEntrySearchRes);
            }
        }
        return result;
    }

    /**
     * 根据ConfigEntry 的 ItemStructureType,将configItem转换换为对应的对象<br/>
     * map->LinkedHashMap、list、tree->ArrayList
     * @param configEntry
     * @return
     */
    private Object formatConfigItems(ConfigEntry configEntry) {
        // format map type config
        if(CfigConstants.CONFIGENTRY_ITEMSTRUCTURETYPE_MAP.equals(configEntry.getItemStructureType())){
            Map<String,String> map = new LinkedHashMap<>();
            if(CollectionUtils.isNotEmpty(configEntry.getConfigItemList())){
                for (ConfigItem configItem : configEntry.getConfigItemList()) {
                    map.put(configItem.getKey(), configItem.getValue());
                }
            }
            return map;
        }
        // format list type config
        if(CfigConstants.CONFIGENTRY_ITEMSTRUCTURETYPE_LIST.equals(configEntry.getItemStructureType())){
            List array = new ArrayList();
            if(CollectionUtils.isNotEmpty(configEntry.getConfigItemList())){
                for (ConfigItem configItem : configEntry.getConfigItemList()) {
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("name", configItem.getName());
                    map.put("value", configItem.getValue());
                    array.add(map);
                }
            }
            return array;
        }
        // format simpletree type config
        if(CfigConstants.CONFIGENTRY_ITEMSTRUCTURETYPE_SIMPLETREE.equals(configEntry.getItemStructureType())){
            List array = new ArrayList();
            if(CollectionUtils.isNotEmpty(configEntry.getConfigItemList())){
                for (ConfigItem configItem : configEntry.getConfigItemList()) {
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("id", configItem.getId());
                    map.put("name", configItem.getName());
                    map.put("value", configItem.getValue());
                    map.put("parentId",configItem.getParentId());
                    array.add(map);
                }
            }
            return array;
        }
        log.warn("not supported configentry type,entry code = {},format by default",configEntry.getCode());
        List array = new ArrayList();
        if(CollectionUtils.isNotEmpty(configEntry.getConfigItemList())){
            for (ConfigItem configItem : configEntry.getConfigItemList()) {
                array.add(array);
            }
        }
        return array;
    }
}
