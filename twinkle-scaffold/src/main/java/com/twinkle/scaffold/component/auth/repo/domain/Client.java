package com.twinkle.scaffold.component.auth.repo.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月2日 下午10:04:54 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@Entity(name="oauth_client")
public class Client implements Serializable{

    @Id
    @Column(name="CLIENT_ID")
    private String clientId;
    
    @Column(name="CLIENT_SECRET")
    private String clientSecret;
    
    @Column(name="REDIRECT_URL")
    private String redirectUrl;
    
    @Column(name="GRANT_TYPE")
    private String grantType;
    
    @Column(name="SCOPE")
    private String scope;
    
    @Column(name="CREATED_TIME")
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME")
    private Timestamp updatedTime;
}
