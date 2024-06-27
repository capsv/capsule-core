FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/assay-passing-service-0.0.1-SNAPSHOT.jar /app/assay-passing-service.jar
CMD ["sh", "-c", "sleep 20 && java -jar assay-passing-service.jar"]