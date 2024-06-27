FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/tasks-service-0.0.1-SNAPSHOT.jar /app/tasks-service.jar
CMD ["sh", "-c", "sleep 20 && java -jar tasks-service.jar"]