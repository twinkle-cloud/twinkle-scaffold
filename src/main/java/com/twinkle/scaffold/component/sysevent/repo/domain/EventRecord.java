package com.twinkle.scaffold.component.sysevent.repo.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月18日 下午4:45:36 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
@Entity(name="sys_event_record")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class EventRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
    
    @Column(name="CODE")
    private String code;
    
    @Type(type = "json" )
    @Column(name="DATA",columnDefinition = "json" )
    private Object data;
    
    @Column(name="CLASS_NAME")
    private String className;
    
    @Column(name="METHOD_NAME")
    private String methodName;
    
    @Column(name="ERROR_DETAIL")
    private String errorDetail;
    
    @Column(name="INVOKE_TIME")
    private Long invokeTime;
    
    @Column(name="STATUS",insertable=false)
    private Byte status = 1;
    
    @Column(name="CREATED_TIME",insertable=false,updatable=false)
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME",insertable=false,updatable=false)
    private Timestamp updatedTime;
}
