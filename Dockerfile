FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

LABEL authors="THAVISHA"

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# --- TARGET: LOCAL DEVELOPMENT ---
FROM builder AS local
EXPOSE 8080
CMD ["mvn", "spring-boot:run", "-Dmaven.test.skip=true"]

# --- TARGET: PRODUCTION RELEASE ---
FROM eclipse-temurin:21-jre-alpine AS production
WORKDIR /app

COPY --from=builder /app /app-build
RUN cd /app-build && mvn clean package -Dmaven.test.skip=true && cp target/*.jar /app/app.jar && rm -rf /app-build

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]