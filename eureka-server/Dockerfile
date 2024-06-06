FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar
CMD ["java", "-jar", "/app/eureka-server.jar"]