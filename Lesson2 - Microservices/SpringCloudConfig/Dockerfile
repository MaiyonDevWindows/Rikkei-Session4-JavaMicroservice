FROM eclipse-temurin:21-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} spring-cloud-config.jar
ENTRYPOINT ["java", "-jar", "spring-cloud-config.jar"]
LABEL authors="maiyon9x"