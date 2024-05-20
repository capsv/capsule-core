FROM openjdk:17

WORKDIR /app
COPY target/vivid-log-service-0.0.1-SNAPSHOT.jar /app/vivid-log-service.jar

CMD ["sh", "-c", "sleep 20 && java -jar /app/vivid-log-service.jar"]