package com.twinkle.scaffold.component.auth.repo.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月27日 下午9:32:15 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@Entity(name="ac_resource_operation")
public class ResourceOperation implements Serializable{

    @Id
    @Column(name="CODE")
    private String code;
    
    @Column(name="NAME")
    private String name;
    
    @Id
    @Column(name="RESOURCE_CODE")
    private String resourceCode;
    
    @Column(name="CREATED_TIME")
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME")
    private Timestamp updatedTime;
    
    @ManyToMany
    @JoinTable(
            name="ac_role_operation_map",
            joinColumns={
                         @JoinColumn(name="RESOURCE_OPERATION_CODE",referencedColumnName="CODE"),
                         @JoinColumn(name="RESOURCE_CODE",referencedColumnName="RESOURCE_CODE")
                        },
            inverseJoinColumns={@JoinColumn(name="ROLE_CODE",referencedColumnName="CODE")}
    )
    private List<Role> roleList;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RESOURCE_CODE")
    private Resource resource;
}
