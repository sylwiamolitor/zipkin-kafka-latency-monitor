FROM openjdk:26-ea-jdk-slim
WORKDIR /app
COPY target/kafkatest-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]