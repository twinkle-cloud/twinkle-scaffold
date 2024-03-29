-- create file_file_entry
CREATE TABLE IF NOT EXISTS file_file_entry (
  ID varchar(255) NOT NULL,
  NAME varchar(255) NOT NULL COMMENT '文件名',
  SIZE bigint NOT NULL COMMENT '文件大小 单位字节',
  CONTENT_TYPE varchar(100) NOT NULL COMMENT 'CONTENT_TYPE',
  CONTENT mediumblob NOT NULL COMMENT '文件CONTENT',
  STATUS tinyint(4) NOT NULL DEFAULT '1',
  CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  PRIMARY KEY (`ID`)
);