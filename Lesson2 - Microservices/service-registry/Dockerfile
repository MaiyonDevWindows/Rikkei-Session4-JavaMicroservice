FROM eclipse-temurin:17-jdk
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} server-registry.jar
ENTRYPOINT ["java", "-jar", "server-registry.jar"]
LABEL authors="maiyon9x"