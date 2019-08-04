package com.twinkle.scaffold.component.auth.repo.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月27日 下午9:31:47 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@Entity(name="ac_resource")
public class Resource implements Serializable{

    @Id
    private String code;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="TYPE")
    private Byte type;
    
    @Column(name="DESCRIPTION")
    private String description;
    
    @Column(name="URL")
    private String url;
    
    @Column(name="STATUS")
    private Byte status;
    
    @Column(name="CREATED_TIME")
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME")
    private Timestamp updatedTime;
    
    @OneToMany(mappedBy="resource")
    private List<ResourceOperation> operationList;
}
