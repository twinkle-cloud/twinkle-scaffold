-- 认证与访问控制
-- drop tables
DROP TABLE IF EXISTS ac_user_role_map;
DROP TABLE IF EXISTS ac_user;
DROP TABLE IF EXISTS ac_role_operation_map;
DROP TABLE IF EXISTS ac_role;
DROP TABLE IF EXISTS ac_resource_operation;
DROP TABLE IF EXISTS ac_resource;

-- create ac_resource
CREATE TABLE IF NOT EXISTS ac_resource (
    CODE varchar(50) NOT NULL COMMENT '资源code码，唯一标识资源',
    NAME varchar(100) NOT NULL COMMENT '资源名字',
    TYPE tinyint NOT NULL COMMENT '资源类型 1-http api 2-页面菜单',
    DESCRIPTION varchar(255) NULL COMMENT '资源描述',
    URL varchar(255) NULL COMMENT '附加字段api url地址' ,
    STATUS tinyint NOT NULL DEFAULT 1 COMMENT '资源状态 0-挂起 1-启用',
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (`CODE`)
);

-- create ac_resource_operation
CREATE TABLE IF NOT EXISTS ac_resource_operation (
    CODE varchar(50) NOT NULL COMMENT '操作code码',
    NAME varchar(100) NOT NULL COMMENT '操作名字',
    RESOURCE_CODE varchar(50) NOT NULL COMMENT '资源code码',
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (`CODE`,`RESOURCE_CODE`),
    FOREIGN KEY (RESOURCE_CODE) REFERENCES ac_resource(CODE)
);

-- create ac_role
CREATE TABLE IF NOT EXISTS ac_role (
    CODE varchar(50) NOT NULL COMMENT '角色code码',
    NAME varchar(100) NOT NULL COMMENT '角色名',
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (`CODE`)
);

-- create ac_role_operation_map
CREATE TABLE IF NOT EXISTS ac_role_operation_map (
    ROLE_CODE varchar(50) NOT NULL COMMENT '角色code码',
    RESOURCE_OPERATION_CODE varchar(50) NOT NULL COMMENT '操作code码',
    RESOURCE_CODE varchar(50) NOT NULL COMMENT '资源code码',
    PRIMARY KEY (`ROLE_CODE`,`RESOURCE_OPERATION_CODE`,`RESOURCE_CODE`),
    FOREIGN KEY (ROLE_CODE) REFERENCES ac_role(CODE),
    FOREIGN KEY (RESOURCE_OPERATION_CODE,RESOURCE_CODE) REFERENCES ac_resource_operation(CODE,RESOURCE_CODE)
);

-- create ac_user
CREATE TABLE IF NOT EXISTS ac_user (
    ID  bigint(20) NOT NULL AUTO_INCREMENT ,
    LOGIN_NAME  varchar(255) NULL COMMENT '登录账号',
    PHONE_NUMBER  varchar(20) NULL COMMENT '登录手机号',
    EMAIL  varchar(255) NULL COMMENT '登录邮件',
    PASSWORD  varchar(1000) NULL COMMENT '登录密码',
    WX_OPENID  varchar(255) NULL COMMENT '登录微信openId',
    WX_UNIONID  varchar(255) NULL COMMENT '登录微信unionId',
    REGISTER_SOURCE  varchar(255) NULL COMMENT '注册来源',
    STATUS  tinyint NULL DEFAULT 1 ,
    CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    PRIMARY KEY (`ID`)
);

-- create ac_user_role_map
CREATE TABLE IF NOT EXISTS ac_user_role_map (
    USER_ID  bigint(20) NOT NULL ,
    ROLE_CODE varchar(50) NOT NULL COMMENT '角色code码',
    PRIMARY KEY (`USER_ID`,`ROLE_CODE`),
    FOREIGN KEY (USER_ID) REFERENCES ac_user(ID),
    FOREIGN KEY (ROLE_CODE) REFERENCES ac_role(CODE)
);


-- create oauth_client
CREATE TABLE IF NOT EXISTS oauth_client (
  CLIENT_ID varchar(50) NOT NULL,
  CLIENT_SECRET varchar(1000) NOT NULL,
  REDIRECT_URL varchar(2000),
  GRANT_TYPE varchar(100) NOT NULL,
  SCOPE varchar(100) NOT NULL,
  CREATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  UPDATED_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  PRIMARY KEY (`CLIENT_ID`)
);

-- create oauth_access_token
CREATE TABLE IF NOT EXISTS oauth_access_token (
  TOKEN_ID VARCHAR(256),
  TOKEN longblob,
  AUTHENTICATION_ID VARCHAR(256) PRIMARY KEY,
  USER_NAME VARCHAR(256),
  CLIENT_ID VARCHAR(256),
  AUTHENTICATION longblob,
  REFRESH_TOKEN VARCHAR(256)
);

-- create oauth_refresh_token
CREATE TABLE IF NOT EXISTS oauth_refresh_token (
  TOKEN_ID VARCHAR(256),
  TOKEN longblob,
  AUTHENTICATION longblob
);