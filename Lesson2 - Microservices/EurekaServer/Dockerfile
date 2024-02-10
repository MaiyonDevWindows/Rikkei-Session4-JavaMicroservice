FROM eclipse-temurin:21-jdk-alpine
RUN apk update; apk --no-cache add iputils curl busybox-extras
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
LABEL authors="maiyon9x"