FROM amazoncorretto:21-alpine-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY target/devop-integration.jar app.jar

# Expose the port
EXPOSE 8080

# Set the entrypoint
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

