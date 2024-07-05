# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host machine to the container
COPY target/file-compressor-0.0.1-SNAPSHOT.jar /app/file.jar 

# Expose port 8082
EXPOSE 8082

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "/app/file.jar"]
