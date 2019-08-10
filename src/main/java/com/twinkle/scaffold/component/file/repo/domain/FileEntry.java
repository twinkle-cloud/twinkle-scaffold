package com.twinkle.scaffold.component.file.repo.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.ToString;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月10日 下午10:10:53 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Data
@ToString
@Entity(name="file_file_entry")
public class FileEntry implements Serializable{
    
    @Id
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="SIZE")
    private Long size;
    
    @Column(name="CONTENT_TYPE")
    private String contentType;
    
    @Column(name="CONTENT")
    private Blob content;
    
    @Column(name="STATUS",insertable=false)
    private Byte status;
    
    @Column(name="CREATED_TIME",insertable=false,updatable=false)
    private Timestamp createdTime;
    
    @Column(name="UPDATED_TIME",insertable=false,updatable=false)
    private Timestamp updatedTime;

}
