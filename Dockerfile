# Stage 1: Build with Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw mvnw.cmd ./
COPY src/ src/

RUN mvn clean package -DskipTests

# Stage 2: Runtime with slim JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy built artifact from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8087

ENTRYPOINT ["java", "-jar", "app.jar"]
