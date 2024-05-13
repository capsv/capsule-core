FROM openjdk:17

WORKDIR /app
COPY target/account-management-service-0.0.1-SNAPSHOT.jar /app/account-management-service.jar

CMD ["sh", "-c", "sleep 20 && java -jar /app/account-management-service.jar"]