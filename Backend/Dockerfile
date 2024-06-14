# 使用 Oracle JDK 17 作为基础镜像
FROM openjdk:17-oracle

# 创建临时目录
VOLUME /tmp

# 添加 JAR 文件到镜像中，并重命名为 app.jar
ADD ./target/Backend-0.0.1-SNAPSHOT.jar app.jar

# 设置容器启动时的命令，激活生产环境配置
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=pro"]
