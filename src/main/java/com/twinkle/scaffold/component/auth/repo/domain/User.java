package com.twinkle.scaffold.component.auth.repo.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年7月27日 下午9:31:39 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
@Entity(name="ac_user")
public class User implements Serializable{

    @Id
    private Long id;
    
    @Column(name="LOGIN_NAME")
    private String loginName;
    
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="PASSWORD")
    private String password;
    
    @Column(name="WX_OPENID")
    private String wxOpenid;
    
    @Column(name="WX_UNIONID")
    private String wxUnionid;
    
    @Column(name="REGISTER_SOURCE")
    private String registerSource;
    
    @Column(name="STATUS")
    private Byte status;
    
    @Column(name="CREATED_TIME")
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME")
    private Timestamp updatedTime;
    
    @ManyToMany(mappedBy="userList",fetch = FetchType.EAGER)
    private List<Role> roleList;
}
