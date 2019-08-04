package com.twinkle.scaffold.component.cfig.repo.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Data;

/**
 * 配置实体 <br/>
 * Date:    2019年8月3日 下午8:42:46 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@Entity(name="cfig_config_entry")
public class ConfigEntry implements Serializable{
    
    @Id
    private String code;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="SCOPE")
    private String scope;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="ITEM_STRUCTURE_TYPE")
    private Byte itemStructureType;
    
    @Column(name="CREATED_TIME")
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME")
    private Timestamp updatedTime;

    @OneToMany(mappedBy="configEntry",fetch = FetchType.EAGER)
    @OrderBy("order ASC")
    private List<ConfigItem> configItemList;
    
}
