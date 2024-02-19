FROM eclipse-temurin:17-jdk-alpine
RUN apk update; apk --no-cache add iputils curl busybox-extras
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} order-service.jar
ENTRYPOINT ["java","-jar","order-service.jar"]
LABEL authors="Maiyon"