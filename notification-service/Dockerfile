FROM openjdk:17

WORKDIR /app
COPY target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service.jar

CMD ["sh", "-c", "sleep 20 && java -jar /app/notification-service.jar"]