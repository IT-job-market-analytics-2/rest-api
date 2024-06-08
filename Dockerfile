FROM gradle:latest as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/application.jar"]