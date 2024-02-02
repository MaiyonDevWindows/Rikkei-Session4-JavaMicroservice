FROM eclipse-temurin:21-jdk-alpine
RUN apk --no-cache add curl iputils
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
LABEL authors="maiyon9x"