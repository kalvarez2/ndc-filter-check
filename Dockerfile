FROM openjdk:8-jdk-alpine
RUN apk --no-cache add curl
VOLUME /tmp
EXPOSE 8888
ADD target/ndc-filter-check-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
