FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY target/email-verify-service-0.0.1-SNAPSHOT.jar /app/email-verify-service.jar
CMD ["sh", "-c", "sleep 20 && java -jar email-verify-service.jar"]