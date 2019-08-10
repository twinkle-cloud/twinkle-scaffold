package com.twinkle.scaffold.component.cfig.repo.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午8:47:17 <br/>
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
@Entity(name="cfig_config_item")
public class ConfigItem implements Serializable{
    
    @Id
    private Long id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="VALUE_TYPE")
    private String valueType;
    
    @Column(name="VALUE")
    private String value;
    
    @Column(name="ORDER")
    private Integer order;
    
    @Column(name="KEY")
    private String key;
    
    @Column(name="TREE_LEVEL")
    private String treeLevel;
    
    @Column(name="PARENT_ID")
    private Long parentId;
    
    @Column(name="CREATED_TIME",insertable=false,updatable=false)
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME",insertable=false,updatable=false)
    private Timestamp updatedTime;
    
    @ManyToOne
    @JoinColumn(name="ENTRY_CODE")
    private ConfigEntry configEntry;
}
