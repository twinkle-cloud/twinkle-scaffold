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

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月27日 下午9:31:33 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
@Entity(name="ac_role")
public class Role implements Serializable{
    
    @Id
    @Column(name="CODE")
    private String code;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="CREATED_TIME")
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME")
    private Timestamp updatedTime;
    
    @ManyToMany
    @JoinTable(
            name="ac_user_role_map",
            joinColumns={@JoinColumn(name="ROLE_CODE",referencedColumnName="CODE")},
            inverseJoinColumns={@JoinColumn(name="USER_ID",referencedColumnName="ID")}
    )
    private List<User> userList;
    
    @ManyToMany(mappedBy="roleList",fetch = FetchType.EAGER)
    private List<ResourceOperation> operationList;
}
