1,该模块主要是对文件管理的抽象
---
    IFileManager.java
    目前只简单实现了数据的方式进行文件管理 JdbcFileManager.java
    可以对接第三方储存服务，进行对接实现
2，api接口主要实现了
---
    文件上传、删除、下载和适合于流媒体的分段下载
3，部署上需要考虑，服务器文件上传的大小限制
---
    数据库目前使用的是mediumblob，单文件最大16M
    spring:
      servlet:
        multipart:
          max-file-size: 16MB
          max-request-size: 16MB