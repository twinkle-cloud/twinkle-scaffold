1,auth模块获取password方式，获取token
---
    POST http://127.0.0.1:8080/oauth/token
    form-data:
        grant_type=password
        username=yk
        password=12345
    headers:
        Authorization:Basic eWs6c2VjcmV0
        #Authorization 的值，为 clientId:clientSecret 做base64加密获得
2，auth模块获取refresh_token方式，获取token
--
    POST http://127.0.0.1:8080/oauth/token
    form-data:
        grant_type=refresh_token
        refresh_token=xxxx
    headers:
        Authorization:Basic eWs6c2VjcmV0
        #Authorization 的值，为 clientId:clientSecret 做base64加密获得
3，auth模块check_token
---
    POST http://127.0.0.1:8080/oauth/check_token
    form-data:
        token=xxx
    headers:
        Authorization:Basic eWs6c2VjcmV0
        #Authorization 的值，为 clientId:clientSecret 做base64加密获得
4, auth模块会拦截 /auth/** ,放行其他
---

