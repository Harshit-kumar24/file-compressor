#stage one defining the version
FROM openjdk:17-jdk-alpine

#defining the directory name
WORKDIR /app

#copying the jar file from the host machine to the container
COPY target/file-compressor-0.0.1-SNAPSHOT.jar  /app/file-compressor-0.0.1-SNAPSHOT.jar

#exposing the port
EXPOSE 8082

#running the jar file
ENTRYPOINT ["java","-jar","/app/file-compressor-0.0.1-SNAPSHOT.jar"]

 