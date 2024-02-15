FROM eclipse-temurin:21-jdk-alpine
RUN apk update; apk --no-cache add iputils curl busybox-extras
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} spring-cloud.jar
ENTRYPOINT ["java", "-jar", "spring-cloud.jar"]
LABEL authors="Maiyon"