FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar
CMD ["sh", "-c", "sleep 20 && java -jar /app/api-gateway.jar"]