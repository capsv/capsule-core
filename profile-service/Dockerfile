FROM openjdk:17

WORKDIR /app
COPY target/profile-service-0.0.1-SNAPSHOT.jar /app/profile-service.jar

CMD ["sh", "-c", "sleep 20 && java -jar /app/profile-service.jar"]