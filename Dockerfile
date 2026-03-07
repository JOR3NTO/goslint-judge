FROM eclipse-temurin:23-jdk-alpine
LABEL authors="goslintgroup"
WORKDIR /app
COPY target/goslint-judge-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "goslint-judge-0.0.1-SNAPSHOT.jar"]