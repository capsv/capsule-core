FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/email-verify-sender-service-0.0.1-SNAPSHOT.jar /app/email-verify-sender-service.jar
CMD ["sh", "-c", "sleep 20 && java -jar email-verify-sender-service.jar"]