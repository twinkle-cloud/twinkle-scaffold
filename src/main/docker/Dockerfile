FROM registry.cn-hangzhou.aliyuncs.com/yk-cloud/yk-java8:8-jdk-alpine
ADD twinkle-scaffold*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=test"]