# Etapa 1: build con Maven (puedes cambiar a Gradle si usas Gradle)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: runtime con JDK 21 liviano
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
WORKDIR /app

# Copia el JAR generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto que expone tu microservicio
EXPOSE 8080

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
