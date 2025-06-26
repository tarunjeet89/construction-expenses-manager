# Start with a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file to the container
COPY target/*.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]