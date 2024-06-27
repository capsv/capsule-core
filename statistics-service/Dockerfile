FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/statistics-service-0.0.1-SNAPSHOT.jar /app/statistics-service.jar
CMD ["sh", "-c", "sleep 20 && java -jar statistics-service.jar"]