# Build Stage
FROM gradle:8-jdk22-alpine AS builder

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .

RUN gradle build -x test --no-daemon

# Runtime Stage
FROM openjdk:22-jdk

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/inventory-service.jar

ENV SPRING_DATASOURCE_USERNAME=admin \
    SPRING_DATASOURCE_PASSWORD=password

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/app/inventory-service.jar"]
