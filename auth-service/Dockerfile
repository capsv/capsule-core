FROM openjdk:17

WORKDIR /app
COPY target/auth-service-0.8.0.jar /app/auth-service.jar

CMD ["sh", "-c", "sleep 20 && java -jar auth-service.jar"]