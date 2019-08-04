-- 配置管理
-- drop tables
DROP TABLE IF EXISTS cfig_config_item;
DROP TABLE IF EXISTS cfig_config_entry;

-- create cfig_config_entry
CREATE TABLE IF NOT EXISTS cfig_config_entry (
    CODE varchar(50) NOT NULL COMMENT '配置实体CODE码' ,
    NAME varchar(100) NOT NULL COMMENT '配置实体名' ,
    SCOPE varchar(255) NOT NULL COMMENT '配置作用范围 app/service/web' ,
    DESCRIPTION varchar(255) NULL COMMENT '配置实体描述' ,
    ITEM_STRUCTURE_TYPE  tinyint NOT NULL COMMENT '配置项结构类型 1-列表 2-字典 3-单父节点树' ,
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (`CODE`)
);

-- create cfig_config_item
CREATE TABLE IF NOT EXISTS cfig_config_item (
    ID  bigint(20) NOT NULL AUTO_INCREMENT ,
    ENTRY_CODE  varchar(50) NOT NULL COMMENT '配置实体CODE码' ,
    `NAME` varchar(100) NULL COMMENT '配置项名' ,
    VALUE_TYPE  tinyint NOT NULL COMMENT '配置项值类型 1-数字 2-字符串 3-json ' ,
    `VALUE` varchar(500) NOT NULL COMMENT '配置项值' ,
    `ORDER` int NOT NULL COMMENT '配置项排序' ,
    `KEY` varchar(100) NULL COMMENT '字典类配置的key' ,
    TREE_LEVEL int NULL COMMENT '配置项树型结构层数' ,
    PARENT_ID  bigint(20) NULL COMMENT '配置项树型结构父级ID' ,
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (`ID`),
    FOREIGN KEY (ENTRY_CODE) REFERENCES cfig_config_entry(CODE)
);