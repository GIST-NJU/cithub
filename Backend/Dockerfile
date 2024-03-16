FROM openjdk:17-oracle
VOLUME /tmp
ADD ./target/Backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]