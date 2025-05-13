FROM ubuntu:latest
LABEL authors="sebastian"

ENTRYPOINT ["top", "-b"]

# Etapa 1: construir o projeto
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: rodar o app
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/ClinicaApplication-*.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "app.jar"]

