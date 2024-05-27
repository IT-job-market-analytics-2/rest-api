FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /root
COPY . .
RUN ./gradlew bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /root/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]