#-----------------
# Build Stage
#-----------------
FROM gradle:8-jdk23-alpine AS builder

# Copy the project files into the container
COPY --chown=gradle:gradle . /home/gradle/src

# Set the working directory
WORKDIR /home/gradle/src

# Build the project
RUN gradle build -x test

#------------------
# Package Stage
#------------------
FROM openjdk:23-jdk

# Create the app directory in the container
RUN mkdir /app

# Copy the built JAR file from the build stage to the runtime stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/api-gateway.jar

# Expose the port
EXPOSE 9000

# Run the application
ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
