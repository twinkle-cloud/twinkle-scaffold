-- create file_file_entry
CREATE TABLE IF NOT EXISTS sys_event_record (
    ID  bigint NOT NULL AUTO_INCREMENT ,
    CODE  varchar(50) NOT NULL COMMENT '事件code码',
    DATA  json NULL COMMENT '事件数据',
    CLASS_NAME  varchar(255) NULL ,
    METHOD_NAME  varchar(255) NULL ,
    ERROR_DETAIL  text NULL ,
    invokeTime  int NULL COMMENT '方法执行时长 单位ms',
    STATUS tinyint(4) NOT NULL DEFAULT '1',
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (ID)
);

