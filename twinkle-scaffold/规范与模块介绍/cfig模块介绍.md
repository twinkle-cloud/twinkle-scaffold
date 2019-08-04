1,api获取配置信息
---
    GET http://localhost:8080/noauth/v1/configentrys?code=%5B%22MAP_1%22%2C%22LIST_1%22%2C%22TREE_1%22%5D&scope=web
    #code=["MAP_1","LIST_1","TREE_1"]
    GET http://localhost:8080/noauth/v1/configentrys?code=MAP_1&scope=web
2，service内部获取配置信息
--
    com.twinkle.scaffold.component.cfig.ConfigComp.getServiceMapConfig(String)
    com.twinkle.scaffold.component.cfig.ConfigComp.getServiceMapConfig(String, String)

