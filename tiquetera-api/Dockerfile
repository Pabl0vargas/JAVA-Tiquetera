# ETAPA 1: Construcción (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Empaquetar saltando tests para agilizar el build en Docker
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos solo el JAR generado en la etapa anterior
COPY --from=build /app/target/tiquetera-api-0.0.1-SNAPSHOT.jar app.jar

# Variables de entorno por defecto
ENV DB_URL=jdbc:mysql://mysql-db:3306/tiqueteradb
ENV DB_DRIVER=com.mysql.cj.jdbc.Driver

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]