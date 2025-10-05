# Etapa de build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B dependency:go-offline -DskipTests
COPY src ./src
RUN mvn -q -B package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jre
ENV JVM_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"
WORKDIR /opt/app
# Crear usuario no root
RUN useradd -r -u 10001 appuser
USER appuser
# Copiar jar
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JVM_OPTS -Dserver.port=8081 -jar app.jar"]
