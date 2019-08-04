-- 认证与访问控制
-- init ac_resource
INSERT INTO ac_resource (
    CODE,
    NAME,
    TYPE,
    DESCRIPTION,
    URL,
    STATUS,
    CREATED_TIME,
    UPDATED_TIME
)
VALUES
    (
        'R1',
        '资源1',
        '1',
        '资源1是一个API',
        '/xx',
        '1',
        '2019-07-28 10:52:30',
        '2019-07-28 10:52:30'
    );
    
-- init ac_resource_operation
INSERT INTO ac_resource_operation (
    CODE,
    NAME,
    RESOURCE_CODE,
    CREATED_TIME,
    UPDATED_TIME
)
VALUES
    (
        'ACCESS_R1',
        '请求R1',
        'R1',
        '2019-07-28 10:55:48',
        '2019-07-28 10:55:48'
    );

-- init ac_role
INSERT INTO ac_role (
    CODE,
    NAME,
    CREATED_TIME,
    UPDATED_TIME
)
VALUES
    (
        'ROLE1',
        '角色1',
        '2019-07-28 10:57:59',
        '2019-07-28 10:57:59'
    );

-- init ac_role
INSERT INTO ac_role_operation_map (
    ROLE_CODE,
    RESOURCE_OPERATION_CODE,
    RESOURCE_CODE
)
VALUES
    ('ROLE1', 'ACCESS_R1', 'R1');
    
-- init ac_user
INSERT INTO ac_user (
    ID,
    LOGIN_NAME,
    PASSWORD,
    STATUS,
    CREATED_TIME,
    UPDATED_TIME
)
VALUES
    (
        '1',
        'yk',
        '$2a$06$59yFadlGxWLYQA6M/ta5kerxzDNbgelW2n4VcIzIigpgDkQcUKmsG',
        '1',
        '2019-07-28 11:00:06',
        '2019-07-28 11:00:06'
    );

-- init ac_user_role_map
INSERT INTO ac_user_role_map (USER_ID, ROLE_CODE) VALUES ('1', 'ROLE1');

-- init oauth_client
INSERT INTO oauth_client (
    CLIENT_ID,
    CLIENT_SECRET,
    REDIRECT_URL,
    GRANT_TYPE,
    SCOPE
)
VALUES
    (
        'yk',
        '$2a$06$BsfwVCjL2vLaJCI92P/KfO7CT.iJjxyGrekJvr1MaIHjSKivdxukW',
        NULL,
        'implicit,password,refresh_token,client_credentials',
        'read,write,trust'
    );

