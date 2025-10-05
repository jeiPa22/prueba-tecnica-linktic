# ===== Etapa de build =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -B dependency:go-offline -DskipTests

COPY src ./src

RUN mvn -q -B clean package -DskipTests

# ===== Etapa de runtime =====
FROM eclipse-temurin:21-jre
WORKDIR /opt/app

# Usuario no root (Ubuntu base -> usar groupadd/useradd)
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Variables configurables
ENV SPRING_PROFILES_ACTIVE=docker \
    SERVER_PORT=8080 \
    JVM_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"

# Copiar el JAR
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto configurable
EXPOSE ${SERVER_PORT}

# Entrypoint parametrizable con perfil y puerto
ENTRYPOINT ["sh","-c","java ${JVM_OPTS} -Dserver.port=${SERVER_PORT} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -jar app.jar"]
